# Chapter 1: Java 基礎文法

## 🎯 学習目標

- Javaの変数・型・スコープを理解する
- 制御構文（if / switch / for / while）を使いこなす
- メソッドの定義・呼び出し・オーバーロードを習得する
- String操作と主要なコレクション（List / Map / Set）を扱える

---

## 📚 学習トピック

### 1.1 変数と型

**プリミティブ型:**
- `int`, `long`, `double`, `float`
- `boolean`
- `char`, `byte`, `short`

**参照型:**
- `String`
- 配列（`int[]`, `String[]`）
- クラス・インターフェース

**型の特徴:**
- 型推論（`var` キーワード — Java 10+）
- 自動型変換（widening）と明示的キャスト
- `final` によるイミュータブル変数

### 1.2 制御構文

- `if / else if / else`
- `switch` 式（Java 14+ の Arrow switch）
- `for` / 拡張 `for` (for-each)
- `while` / `do-while`
- `break` / `continue` / ラベル付きbreak

### 1.3 メソッド

- メソッドの定義と呼び出し
- 引数と戻り値の型
- メソッドのオーバーロード
- 可変長引数（varargs）
- `static` メソッドとインスタンスメソッドの違い

### 1.4 String 操作

- `String` の不変性
- 主要メソッド: `length()`, `substring()`, `contains()`, `replace()`, `split()`, `trim()`, `strip()`
- 文字列フォーマット: `String.format()` / テキストブロック（Java 15+）
- `StringBuilder` による可変文字列操作

### 1.5 コレクション

**List:**
- `ArrayList` — 順序あり、重複あり
- `List.of()` — イミュータブルリスト

**Map:**
- `HashMap` — キーと値のペア
- `LinkedHashMap` — 挿入順を保持
- `Map.of()` — イミュータブルマップ

**Set:**
- `HashSet` — 重複なし、順序なし
- `LinkedHashSet` — 挿入順を保持

**コレクション操作:**
- `stream()` の基礎 (`filter`, `map`, `collect`)
- `forEach` / `removeIf`

---

## 🔗 関連リソース

- [examples/01-java-basics/](../../examples/01-java-basics/) — サンプルコード
- [exercises/01-java-basics/problem/](../../exercises/01-java-basics/problem/) — 演習問題
- [exercises/01-java-basics/solution/](../../exercises/01-java-basics/solution/) — 解答例

---

## ⏱️ 推奨学習時間

2週間（20〜25時間）

---

## ✅ チェックリスト

- [ ] `int` と `Integer` の違いを説明できる
- [ ] `var` を適切に使える
- [ ] Arrow switch を使える
- [ ] for-each と Stream の `forEach` を使い分けられる
- [ ] `String.format()` とテキストブロックを使える
- [ ] `ArrayList` と `HashMap` を使ってデータを管理できる
- [ ] Stream の `filter` / `map` / `collect` の基本を理解している

---

## 📝 次の章

[Chapter 2: オブジェクト指向](../02-oop/README.md)
