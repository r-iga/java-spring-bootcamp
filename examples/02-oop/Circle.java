package bootcamp.oop;

/**
 * Chapter 2: Circle クラス — Shape を継承し Drawable を実装
 */
public class Circle extends Shape implements Drawable {

    private final double radius;

    public Circle(String color, double radius) {
        super(color);  // 親クラスのコンストラクタを呼ぶ
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void draw() {
        System.out.println("○ 円（半径=" + radius + ", 色=" + getColor() + "）を描画");
    }
}
