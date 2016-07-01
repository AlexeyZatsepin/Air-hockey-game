package study.example.azatsepin.airhockey.utils.geometry;

public class Point {
    public final float x, y, z;
    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point translateY(float distance) {
        return new Point(x, y + distance, z);
    }
}
