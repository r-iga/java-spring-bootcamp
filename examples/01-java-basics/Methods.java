package bootcamp.basics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Chapter 1: メソッドのサンプル
 *
 * 実行方法:
 *   javac Methods.java && java bootcamp.basics.Methods
 */
public class Methods {

    public static void main(String[] args) {
        // ─── 1. 基本的なメソッド呼び出し ─────────────────────
        System.out.println("=== 基本メソッド ===");
        System.out.println(greet("太郎"));
        System.out.println(add(10, 20));

        // ─── 2. オーバーロード ────────────────────────────────
        System.out.println("\n=== オーバーロード ===");
        System.out.println(describe("田中"));                     // String版
        System.out.println(describe("田中", 30));                  // String + int版
        System.out.println(describe("田中", 30, "東京"));          // 3引数版

        // ─── 3. 可変長引数（varargs）─────────────────────────
        System.out.println("\n=== varargs ===");
        System.out.println(sum(1, 2, 3));
        System.out.println(sum(10, 20, 30, 40, 50));
        System.out.println(joinWith(" / ", "A", "B", "C"));

        // ─── 4. 再帰 ─────────────────────────────────────────
        System.out.println("\n=== 再帰 ===");
        System.out.println("5! = " + factorial(5));
        System.out.println("fib(10) = " + fibonacci(10));

        // ─── 5. static vs インスタンスメソッド ───────────────
        System.out.println("\n=== static vs インスタンス ===");
        // static メソッドはクラス名で呼び出す（インスタンス不要）
        System.out.println("PI = " + Calculator.PI);
        System.out.println("circle area: " + Calculator.circleArea(5.0));

        // インスタンスメソッドはオブジェクトを作ってから呼び出す
        var calc = new Calculator(100);
        System.out.println("discount(20%): " + calc.discount(0.2));
    }

    // ─── メソッド定義 ─────────────────────────────────────────

    /** 引数と戻り値の型の基本 */
    static String greet(String name) {
        return "こんにちは、" + name + "さん！";
    }

    static int add(int a, int b) {
        return a + b;
    }

    /** オーバーロード: 同名・異なる引数リスト */
    static String describe(String name) {
        return name + "さん";
    }

    static String describe(String name, int age) {
        return name + "さん（" + age + "歳）";
    }

    static String describe(String name, int age, String city) {
        return name + "さん（" + age + "歳）/ " + city + " 在住";
    }

    /** 可変長引数（varargs） — 0個以上の引数を受け取れる */
    static int sum(int... numbers) {
        int total = 0;
        for (int n : numbers) {
            total += n;
        }
        return total;
    }

    static String joinWith(String delimiter, String... parts) {
        return String.join(delimiter, parts);
    }

    /** 再帰メソッド */
    static long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

/** static / インスタンスメソッドのデモ用クラス */
class Calculator {

    static final double PI = Math.PI;  // static フィールド

    private final double base;         // インスタンスフィールド

    Calculator(double base) {
        this.base = base;
    }

    /** static メソッド: インスタンス不要 */
    static double circleArea(double radius) {
        return PI * radius * radius;
    }

    /** インスタンスメソッド: インスタンスのフィールドを使う */
    double discount(double rate) {
        return base * (1 - rate);
    }
}
