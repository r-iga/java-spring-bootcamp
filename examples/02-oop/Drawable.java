package bootcamp.oop;

/**
 * Chapter 2: インターフェース — 描画可能なことを表す
 */
public interface Drawable {
    void draw();

    /** default メソッド — 実装がデフォルトで提供される（Java 8+）*/
    default void drawWithBorder() {
        System.out.println("--- 枠線 ---");
        draw();
        System.out.println("--- 枠線 ---");
    }
}
