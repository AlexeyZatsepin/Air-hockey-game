package study.example.azatsepin.airhockey.utils.geometry;

public class Circle {
    public final Point center;
    public final float radius;
    public Circle(Point center, float radius) {
        this.center = center;
        this.radius = radius;
    }
    public Circle scale(float scale) {
        return new Circle(center, radius * scale);
    }
}
