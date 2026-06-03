package bootcamp.oop;

/**
 * Chapter 2: Rectangle クラス — Shape を継承し Drawable を実装
 */
public class Rectangle extends Shape implements Drawable {

    private final double width;
    private final double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    public boolean isSquare() {
        return width == height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public void draw() {
        System.out.println("□ 長方形（" + width + "×" + height + ", 色=" + getColor() + "）を描画");
    }

    @Override
    public String describe() {
        // 親クラスのメソッドを拡張
        String base = super.describe();
        return base + (isSquare() ? " [正方形]" : "");
    }
}
