# 最終プロジェクト: ブログ記事管理API

## 概要

2ヶ月間の学習の集大成として、Spring Boot + MyBatis を使ったブログ記事管理APIを自力で構築してください。  
設計から実装・テストまでを一人で行い、学んだ全スキルを統合することが目標です。

## 要件

### 機能要件

#### 記事 (Article)

| フィールド | 型 | 制約 |
|-----------|-----|------|
| id | Long | 主キー、自動採番 |
| title | String | NOT NULL, 最大200文字 |
| content | String | NOT NULL |
| author_name | String | NOT NULL, 最大100文字 |
| status | Enum(DRAFT/PUBLISHED) | NOT NULL, デフォルト: DRAFT |
| created_at | LocalDateTime | 自動設定 |
| updated_at | LocalDateTime | 更新時に自動更新 |

#### タグ (Tag)

| フィールド | 型 | 制約 |
|-----------|-----|------|
| id | Long | 主キー、自動採番 |
| name | String | NOT NULL, UNIQUE, 最大50文字 |

#### 記事とタグの関連: 多対多 (article_tags テーブル)

### APIエンドポイント

#### 記事API

```
GET    /api/articles                    記事一覧（ページネーション）
GET    /api/articles/{id}               記事詳細（タグ含む）
POST   /api/articles                    記事作成
PUT    /api/articles/{id}               記事更新
DELETE /api/articles/{id}              記事削除
PATCH  /api/articles/{id}/publish      記事を公開状態に変更
```

#### タグAPI

```
GET    /api/tags                        タグ一覧
POST   /api/tags                        タグ作成
DELETE /api/tags/{id}                  タグ削除
POST   /api/articles/{id}/tags/{tagId} 記事にタグを付ける
DELETE /api/articles/{id}/tags/{tagId} 記事からタグを外す
```

### クエリパラメータ（記事一覧）

```
GET /api/articles?page=0&size=10&status=PUBLISHED&keyword=Spring
```

| パラメータ | 型 | デフォルト | 説明 |
|-----------|-----|-----------|------|
| page | int | 0 | ページ番号（0始まり）|
| size | int | 10 | 1ページあたりの件数（最大50）|
| status | String | (全て) | DRAFT または PUBLISHED |
| keyword | String | (なし) | タイトルまたは本文での部分一致検索 |

### レスポンス例

#### GET /api/articles

```json
{
  "content": [
    {
      "id": 1,
      "title": "Spring Boot入門",
      "authorName": "田中 太郎",
      "status": "PUBLISHED",
      "tags": ["Java", "Spring"],
      "createdAt": "2024-01-15T10:00:00"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1
}
```

#### GET /api/articles/{id}

```json
{
  "id": 1,
  "title": "Spring Boot入門",
  "content": "Spring Bootは...",
  "authorName": "田中 太郎",
  "status": "PUBLISHED",
  "tags": [
    {"id": 1, "name": "Java"},
    {"id": 2, "name": "Spring"}
  ],
  "createdAt": "2024-01-15T10:00:00",
  "updatedAt": "2024-01-16T09:00:00"
}
```

## 実装要件

### アーキテクチャ

- レイヤードアーキテクチャ（Controller / Service / Repository / Mapper）
- DTO パターン（Request / Response の分離）
- Service はインターフェースと実装クラスに分ける

### バリデーション

- `@Valid` + Bean Validation アノテーション使用
- バリデーションエラーは `400 Bad Request` で適切なエラーメッセージを返す

### エラーハンドリング

- `@RestControllerAdvice` による統一エラーレスポンス
- 記事/タグが見つからない場合: `404 Not Found`
- バリデーションエラー: `400 Bad Request`

### データアクセス

- MyBatis XML Mapper を使用（アノテーションではなく XML）
- `<where>` / `<if>` による動的検索
- `<collection>` による タグのEagerロード（N+1問題を避ける）
- `@Transactional` の適切な使用

### テスト

以下のテストを全て作成して `mvn test` でパスすること:

- [ ] `ArticleMapperTest` — `@MybatisTest` による Mapper 単体テスト（CRUD + 検索）
- [ ] `ArticleServiceTest` — Mockito によるサービス単体テスト
- [ ] `ArticleControllerTest` — `@WebMvcTest` によるコントローラーテスト（正常系 + 異常系）

## ディレクトリ構成（参考）

```
projects/final/blog-api/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/bootcamp/blog/
    │   │   ├── BlogApplication.java
    │   │   ├── controller/
    │   │   │   ├── ArticleController.java
    │   │   │   └── TagController.java
    │   │   ├── service/
    │   │   │   ├── ArticleService.java
    │   │   │   ├── TagService.java
    │   │   │   └── impl/
    │   │   │       ├── ArticleServiceImpl.java
    │   │   │       └── TagServiceImpl.java
    │   │   ├── mapper/
    │   │   │   ├── ArticleMapper.java
    │   │   │   └── TagMapper.java
    │   │   ├── entity/
    │   │   │   ├── Article.java
    │   │   │   ├── ArticleStatus.java  (enum)
    │   │   │   └── Tag.java
    │   │   ├── dto/
    │   │   │   ├── CreateArticleRequest.java
    │   │   │   ├── UpdateArticleRequest.java
    │   │   │   ├── ArticleResponse.java
    │   │   │   ├── ArticleListResponse.java
    │   │   │   └── PageResult.java
    │   │   └── exception/
    │   │       ├── ArticleNotFoundException.java
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       ├── application.yml
    │       ├── schema.sql
    │       ├── data.sql
    │       └── mapper/
    │           ├── ArticleMapper.xml
    │           └── TagMapper.xml
    └── test/
        └── java/bootcamp/blog/
            ├── mapper/ArticleMapperTest.java
            ├── service/ArticleServiceTest.java
            └── controller/ArticleControllerTest.java
```

## 採点基準

| 項目 | 点数 |
|------|------|
| 記事CRUDエンドポイントが正しく動作する | 30点 |
| タグCRUD + 記事へのタグ付けが動作する | 20点 |
| ページネーション + 動的検索が動作する | 15点 |
| バリデーション + エラーハンドリング | 15点 |
| テストが全てパスする | 20点 |
| **合計** | **100点** |

## ヒント・参考

- examples/06-mybatis の構成が参考になります
- 多対多のタグ取得には `<collection>` と JOIN を使うか、別クエリを発行するかを選択してください
- ページネーションは `LIMIT #{size} OFFSET #{offset}` で実装できます（offset = page * size）
- 動的検索は `<where>` + `<if>` で実装します（docs/07-mybatis-advanced/README.md 参照）

## 提出方法

1. このプロジェクトを `projects/final/blog-api/` に実装してください
2. `mvn test` が全てパスすることを確認してください
3. README.md に動作確認用の curl コマンドを記載してください
