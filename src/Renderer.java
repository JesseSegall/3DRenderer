import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer {
    public void render(Graphics g, Scene scene, Camera camera, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) g;

        Matrix transform = camera.getTransformationMatrix();
        double[] zBuffer = new double[width * height];
        java.util.Arrays.fill(zBuffer, Double.NEGATIVE_INFINITY);

        double fov = Math.tan(camera.getFov() / 2) * 170;

        List<Triangle> triangles = scene.getTriangles();
        for (Triangle triangle : triangles) {
            Vertex v1 = transform.transform(triangle.v1);
            Vertex v2 = transform.transform(triangle.v2);
            Vertex v3 = transform.transform(triangle.v3);

            Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z, v2.w - v1.w);
            Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z, v3.w - v1.w);
            Vertex norm = new Vertex(
                    ab.y * ac.z - ab.z * ac.y,
                    ab.z * ac.x - ab.x * ac.z,
                    ab.x * ac.y - ab.y * ac.x,
                    1
            );
            double normalLength = Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
            norm.x /= normalLength;
            norm.y /= normalLength;
            norm.z /= normalLength;

            double angleCos = Math.abs(norm.z);

            v1.x = v1.x / (-v1.z) * fov;
            v1.y = v1.y / (-v1.z) * fov;
            v2.x = v2.x / (-v2.z) * fov;
            v2.y = v2.y / (-v2.z) * fov;
            v3.x = v3.x / (-v3.z) * fov;
            v3.y = v3.y / (-v3.z) * fov;

            v1.x += width / 2;
            v1.y += height / 2;
            v2.x += width / 2;
            v2.y += height / 2;
            v3.x += width / 2;
            v3.y += height / 2;


            //Bounding Box calculations
            int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
            int maxX = (int) Math.min(width - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
            int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
            int maxY = (int) Math.min(height - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

            double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

            // Loop through each pixel in the BB
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    // Barycentric Coord Calc
                    double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                    double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                    double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;
                    // Make sure the pixel is inside the triangle
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                        double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                        int zIndex = y * width + x;
                        if (zBuffer[zIndex] < depth) {
                            double shade = Shader.getShade(angleCos);
                            Color color = Shader.shadeColor(triangle.color, shade);
                            image.setRGB(x, y, color.getRGB());
                            zBuffer[zIndex] = depth;
                        }
                    }
                }
            }
        }

        g2d.drawImage(image, 0, 0, null);
    }
}
