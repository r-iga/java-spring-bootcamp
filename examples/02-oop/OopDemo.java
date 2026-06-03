package bootcamp.oop;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Chapter 2: OOP + ラムダ式 + Stream API のデモ
 *
 * 実行方法:
 *   このファイルと同じディレクトリで以下を実行
 *   javac *.java && java bootcamp.oop.OopDemo
 */
public class OopDemo {

    public static void main(String[] args) {
        polymorphismDemo();
        lambdaDemo();
        optionalDemo();
        recordDemo();
    }

    // ─── ポリモーフィズム ─────────────────────────────────────
    static void polymorphismDemo() {
        System.out.println("=== ポリモーフィズム ===");

        // Shape型のリストに Circle も Rectangle も入れられる
        List<Shape> shapes = List.of(
            new Circle("赤", 5.0),
            new Rectangle("青", 4.0, 6.0),
            new Circle("緑", 3.0),
            new Rectangle("黄", 5.0, 5.0)
        );

        // 抽象型 Shape として扱う — 実際の型によって正しいメソッドが呼ばれる
        for (Shape shape : shapes) {
            System.out.println(shape.describe());
        }

        // instanceof + パターンマッチング（Java 16+）
        System.out.println("\n--- 型判定 ---");
        for (Shape shape : shapes) {
            if (shape instanceof Circle c) {
                System.out.println("Circle: 半径=" + c.getRadius());
            } else if (shape instanceof Rectangle r && r.isSquare()) {
                System.out.println("正方形!");
            }
        }

        // Drawable インターフェースとして扱う
        System.out.println("\n--- draw() ---");
        for (Shape shape : shapes) {
            if (shape instanceof Drawable d) {
                d.draw();
            }
        }
    }

    // ─── ラムダ式と Stream ───────────────────────────────────
    static void lambdaDemo() {
        System.out.println("\n=== ラムダ式と Stream ===");

        List<Shape> shapes = List.of(
            new Circle("赤", 5.0),
            new Rectangle("青", 4.0, 6.0),
            new Circle("緑", 3.0),
            new Rectangle("黄", 5.0, 5.0)
        );

        // 面積でソートして出力
        System.out.println("面積の昇順:");
        shapes.stream()
            .sorted(Comparator.comparingDouble(Shape::area))
            .forEach(s -> System.out.printf("  %s: %.2f%n",
                s.getClass().getSimpleName(), s.area()));

        // Circle のみ抽出してリストに
        List<Circle> circles = shapes.stream()
            .filter(s -> s instanceof Circle)
            .map(s -> (Circle) s)
            .collect(Collectors.toList());
        System.out.println("\nCircle の数: " + circles.size());

        // 全図形の合計面積
        double totalArea = shapes.stream()
            .mapToDouble(Shape::area)
            .sum();
        System.out.printf("合計面積: %.2f%n", totalArea);

        // 最大面積の図形
        Optional<Shape> largest = shapes.stream()
            .max(Comparator.comparingDouble(Shape::area));
        largest.ifPresent(s -> System.out.println("最大面積: " + s.describe()));
    }

    // ─── Optional ────────────────────────────────────────────
    static void optionalDemo() {
        System.out.println("\n=== Optional ===");

        List<String> names = List.of("田中", "佐藤", "鈴木");

        // 要素の検索（存在する場合）
        Optional<String> found = names.stream()
            .filter(n -> n.startsWith("佐"))
            .findFirst();

        // orElse — 値がなければデフォルト値
        System.out.println("found: " + found.orElse("なし"));

        // map — 値があれば変換
        String upper = found.map(n -> n + "さん").orElse("ゲスト");
        System.out.println("map: " + upper);

        // 存在しないケース
        Optional<String> notFound = names.stream()
            .filter(n -> n.startsWith("X"))
            .findFirst();
        System.out.println("notFound: " + notFound.orElse("なし"));

        // orElseThrow
        try {
            notFound.orElseThrow(() -> new RuntimeException("見つかりません"));
        } catch (RuntimeException e) {
            System.out.println("例外キャッチ: " + e.getMessage());
        }
    }

    // ─── record クラス（Java 16+）─────────────────────────────
    static void recordDemo() {
        System.out.println("\n=== record クラス ===");

        // record は自動的に equals / hashCode / toString / getter が生成される
        record Point(double x, double y) {
            // カスタムメソッドも追加できる
            double distanceTo(Point other) {
                double dx = this.x - other.x;
                double dy = this.y - other.y;
                return Math.sqrt(dx * dx + dy * dy);
            }
        }

        var p1 = new Point(0, 0);
        var p2 = new Point(3, 4);

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p1.x(): " + p1.x());
        System.out.println("p1 → p2 の距離: " + p1.distanceTo(p2));
        System.out.println("p1.equals(new Point(0,0)): " + p1.equals(new Point(0, 0)));
    }
}
