public class Matrix {
    double[] values;

    Matrix(double[] values) {
        this.values = values;
    }

    Matrix multiplyMatrices(Matrix other) {
        double[] result = new double[16];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int i = 0; i < 4; i++) {
                    result[row * 4 + col] += this.values[row * 4 + i] * other.values[i * 4 + col];
                }
            }
        }
        return new Matrix(result);
    }

    Vertex transform(Vertex vertex) {
        return new Vertex(
                vertex.x * values[0] + vertex.y * values[4] + vertex.z * values[8] + vertex.w * values[12],
                vertex.x * values[1] + vertex.y * values[5] + vertex.z * values[9] + vertex.w * values[13],
                vertex.x * values[2] + vertex.y * values[6] + vertex.z * values[10] + vertex.w * values[14],
                vertex.x * values[3] + vertex.y * values[7] + vertex.z * values[11] + vertex.w * values[15]
        );
    }
    @Override public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                sb.append(values[row * 4 + col]);
                if (col != 3) {
                    sb.append(",");
                }
            }
            if (row != 3) {
                sb.append(";\n ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

