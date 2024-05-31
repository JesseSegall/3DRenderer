import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Triangle> triangles;

    public Scene() {
        triangles = new ArrayList<>();
        initializeTriangles();
    }

    private void initializeTriangles() {
        // Triangle vertices
        Vertex v1 = new Vertex(-100, 100, 100, 1);
        Vertex v2 = new Vertex(100, 100, 100, 1);
        Vertex v3 = new Vertex(-100, 100, -100, 1);
        Vertex v4 = new Vertex(100, 100, -100, 1);
        Vertex v5 = new Vertex(100, -100, 100, 1);
        Vertex v6 = new Vertex(100, -100, -100, 1);
        Vertex v7 = new Vertex(-100, -100, 100, 1);
        Vertex v8 = new Vertex(-100, -100, -100, 1);

        Color color = Color.WHITE;

        triangles.add(new Triangle(v1, v2, v3, color));
        triangles.add(new Triangle(v2, v4, v3, color));
        triangles.add(new Triangle(v5, v4, v2, color));
        triangles.add(new Triangle(v5, v6, v4, color));
        triangles.add(new Triangle(v7, v5, v1, color));
        triangles.add(new Triangle(v5, v2, v1, color));
        triangles.add(new Triangle(v7, v1, v8, color));
        triangles.add(new Triangle(v1, v3, v8, color));
        triangles.add(new Triangle(v3, v4, v8, color));
        triangles.add(new Triangle(v8, v4, v6, color));
        triangles.add(new Triangle(v5, v7, v8, color));
        triangles.add(new Triangle(v8, v6, v5, color));
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }
}
