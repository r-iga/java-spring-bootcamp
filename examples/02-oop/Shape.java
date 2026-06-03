package bootcamp.oop;

/**
 * Chapter 2: 抽象クラス — 図形の基底クラス
 */
public abstract class Shape {

    private final String color;

    public Shape(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    /** サブクラスが必ず実装しなければならないメソッド */
    public abstract double area();

    public abstract double perimeter();

    /** 共通の実装（オーバーライド可能） */
    public String describe() {
        return String.format("%s[色=%s, 面積=%.2f, 周囲長=%.2f]",
            getClass().getSimpleName(), color, area(), perimeter());
    }
}
