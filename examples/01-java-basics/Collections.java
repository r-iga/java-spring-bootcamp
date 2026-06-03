package bootcamp.basics;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Chapter 1: コレクション（List / Map / Set）と Stream API のサンプル
 *
 * 実行方法:
 *   javac Collections.java && java bootcamp.basics.Collections
 */
public class Collections {

    public static void main(String[] args) {
        listExamples();
        mapExamples();
        setExamples();
        streamExamples();
    }

    // ─── List ─────────────────────────────────────────────────
    static void listExamples() {
        System.out.println("=== List ===");

        // ArrayList — 可変長、順序あり
        List<String> names = new ArrayList<>(List.of("田中", "佐藤", "鈴木"));
        names.add("山田");
        names.remove("佐藤");

        System.out.println("names: " + names);
        System.out.println("size: " + names.size());
        System.out.println("get(0): " + names.get(0));
        System.out.println("contains('鈴木'): " + names.contains("鈴木"));

        // ソート
        List<Integer> nums = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6));
        java.util.Collections.sort(nums);
        System.out.println("sorted: " + nums);

        // イミュータブルリスト
        List<String> immutable = List.of("A", "B", "C");
        // immutable.add("D");  // UnsupportedOperationException

        System.out.println("immutable: " + immutable);
    }

    // ─── Map ──────────────────────────────────────────────────
    static void mapExamples() {
        System.out.println("\n=== Map ===");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("数学", 90);
        scores.put("英語", 80);
        scores.put("国語", 85);

        System.out.println("scores: " + scores);
        System.out.println("get('数学'): " + scores.get("数学"));
        System.out.println("containsKey('英語'): " + scores.containsKey("英語"));
        System.out.println("getOrDefault('理科', 0): " + scores.getOrDefault("理科", 0));

        // 全エントリを走査
        System.out.println("全エントリ:");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }

        // putIfAbsent — キーが存在しない場合のみ登録
        scores.putIfAbsent("英語", 100);  // 既存なのでスキップ
        scores.putIfAbsent("理科", 70);   // 新規登録
        System.out.println("putIfAbsent後: " + scores);

        // computeIfAbsent — キーがない場合に計算して登録
        Map<String, List<String>> groups = new HashMap<>();
        groups.computeIfAbsent("A組", k -> new ArrayList<>()).add("田中");
        groups.computeIfAbsent("A組", k -> new ArrayList<>()).add("佐藤");
        System.out.println("groups: " + groups);
    }

    // ─── Set ──────────────────────────────────────────────────
    static void setExamples() {
        System.out.println("\n=== Set ===");

        Set<String> tags = new HashSet<>(Set.of("Java", "Spring", "MyBatis"));
        tags.add("Java");       // 重複は無視される
        tags.add("Docker");

        System.out.println("tags: " + tags);
        System.out.println("contains('Spring'): " + tags.contains("Spring"));
        System.out.println("size: " + tags.size());

        // 積集合・和集合・差集合
        Set<String> setA = new HashSet<>(Set.of("A", "B", "C", "D"));
        Set<String> setB = new HashSet<>(Set.of("C", "D", "E", "F"));

        Set<String> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("交差 (A∩B): " + intersection);

        Set<String> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("和集合 (A∪B): " + union);

        Set<String> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("差集合 (A-B): " + difference);
    }

    // ─── Stream API ───────────────────────────────────────────
    static void streamExamples() {
        System.out.println("\n=== Stream API ===");

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // filter — 条件に合う要素だけ抽出
        List<Integer> evens = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("偶数: " + evens);

        // map — 各要素を変換
        List<Integer> doubled = numbers.stream()
            .map(n -> n * 2)
            .collect(Collectors.toList());
        System.out.println("2倍: " + doubled);

        // filter + map + collect
        List<String> result = numbers.stream()
            .filter(n -> n > 5)
            .map(n -> "item-" + n)
            .collect(Collectors.toList());
        System.out.println("5より大きい要素: " + result);

        // reduce — 集約
        int total = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("合計: " + total);

        // 統計
        OptionalDouble avg = numbers.stream()
            .mapToInt(Integer::intValue)
            .average();
        System.out.println("平均: " + avg.orElse(0));

        // sorted / distinct / limit
        List<Integer> processed = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3).stream()
            .distinct()
            .sorted()
            .limit(5)
            .collect(Collectors.toList());
        System.out.println("distinct→sorted→limit(5): " + processed);

        // groupingBy — グループ化
        List<String> words = List.of("apple", "banana", "avocado", "blueberry", "cherry");
        Map<Character, List<String>> grouped = words.stream()
            .collect(Collectors.groupingBy(w -> w.charAt(0)));
        System.out.println("頭文字でグループ化: " + grouped);

        // anyMatch / allMatch / noneMatch
        boolean anyNegative = numbers.stream().anyMatch(n -> n < 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println("anyNegative: " + anyNegative);
        System.out.println("allPositive: " + allPositive);
    }
}
