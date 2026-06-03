package bootcamp.ex01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 演習 01-1: Java基礎文法 — 解答例
 */
public class Exercise01 {

    public static void main(String[] args) {
        primitiveExercise();
        stringExercise();
        collectionExercise();
        streamExercise();
    }

    static void primitiveExercise() {
        int x = 42;
        double d = x;          // 暗黙的な拡大変換
        int y = (int) d;       // 明示的なキャスト

        System.out.println("x = " + x);
        System.out.println("d = " + d);
        System.out.println("y = " + y);
    }

    static void stringExercise() {
        String name = "Java Spring Bootcamp";

        System.out.println(name.toUpperCase());
        System.out.println(name.toLowerCase());
        System.out.println(name.contains("Spring"));
        System.out.println(name.replace("Java", "Kotlin"));
        System.out.println(name.split(" ").length);
    }

    static void collectionExercise() {
        var fruits = new ArrayList<String>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");

        System.out.println(fruits.size());
        System.out.println(fruits.contains("Apple"));

        var countMap = new HashMap<String, Integer>();
        for (var fruit : fruits) {
            countMap.merge(fruit, 1, Integer::sum);
        }
        System.out.println(countMap);
    }

    static void streamExercise() {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 偶数フィルタ
        System.out.println(numbers.stream().filter(n -> n % 2 == 0).toList());
        // 2乗
        System.out.println(numbers.stream().map(n -> n * n).toList());
        // 合計
        System.out.println(numbers.stream().mapToInt(Integer::intValue).sum());
        // 5より大きい要素が存在するか
        System.out.println(numbers.stream().anyMatch(n -> n > 5));
    }
}
