package bootcamp.ex05;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 演習 05-1: REST API実装
 *
 * 以下の TODO を実装して、BookのCRUD APIを完成させてください。
 */
@SpringBootApplication
public class Exercise05 {
    public static void main(String[] args) {
        SpringApplication.run(Exercise05.class, args);
    }
}

// ─── DTOレコード ──────────────────────────────────────────────────────────────

/**
 * TODO 1: CreateBookRequest レコードを定義してください
 *
 * フィールドとバリデーション:
 *   - title:  @NotBlank  + @Size(max = 200)
 *   - author: @NotBlank  + @Size(max = 100)
 *   - isbn:   @NotBlank  + @Size(min = 10, max = 13)
 */
// TODO: CreateBookRequest を定義してください
record CreateBookRequest() {}

/**
 * TODO 2: BookResponse レコードを定義してください
 *
 * フィールド: Long id, String title, String author, String isbn, LocalDateTime createdAt
 */
// TODO: BookResponse を定義してください
record BookResponse() {}

// ─── コントローラー ──────────────────────────────────────────────────────────

/**
 * TODO 3: BookController を実装してください
 *
 * @RestController @RequestMapping("/api/books")
 *
 * インメモリストレージ（参考）:
 *   private final Map<Long, BookData> store = new ConcurrentHashMap<>();
 *   private final AtomicLong idSeq = new AtomicLong(1);
 *
 * エンドポイント:
 *   GET  /api/books      — 一覧取得 → List<BookResponse>
 *   GET  /api/books/{id} — 1件取得 → BookResponse / 404
 *   POST /api/books      — 作成 (@Valid @RequestBody CreateBookRequest) → 201 + BookResponse
 *   DELETE /api/books/{id} — 削除 → 204
 *
 * ヒント: コンストラクタで初期データを2件追加しておくと動作確認しやすい
 */
// TODO: BookController を実装してください
@RestController
@RequestMapping("/api/books")
class BookController {
    // TODO: ここに実装してください
}
