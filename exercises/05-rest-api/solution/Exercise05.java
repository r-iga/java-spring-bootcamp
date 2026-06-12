package bootcamp.ex05;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 演習 05-1: REST API実装 — 解答例
 */
@SpringBootApplication
public class Exercise05 {
    public static void main(String[] args) {
        SpringApplication.run(Exercise05.class, args);
    }
}

record CreateBookRequest(
    @NotBlank @Size(max = 200) String title,
    @NotBlank @Size(max = 100) String author,
    @NotBlank @Size(min = 10, max = 13) String isbn
) {}

record BookResponse(Long id, String title, String author, String isbn, LocalDateTime createdAt) {}

@RestController
@RequestMapping("/api/books")
class BookController {

    private final List<BookData> store = new List<>();
    private final AtomicLong idSeq = new AtomicLong(1);

    public BookController() {
        add("Clean Code", "Robert C. Martin", "9780132350884");
        add("Effective Java", "Joshua Bloch", "9780134685991");
    }

    @GetMapping
    public List<BookResponse> findAll() {
        return store.stream()
                .sorted((a, b) -> Long.compare(a.id(), b.id()))
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable Long id) {
        var book = store.get(id);
        return book != null ? ResponseEntity.ok(toResponse(book)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody CreateBookRequest req) {
        return toResponse(add(req.title(), req.author(), req.isbn()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!store.map( book -> book.id).contains(id)) return ResponseEntity.notFound().build();
        store.remove(id);
        return ResponseEntity.noContent().build();
    }

    private BookData add(String title, String author, String isbn) {
        long id = idSeq.getAndIncrement();
        var book = new BookData(id, title, author, isbn, LocalDateTime.now());
        store.add(book);
        return book;
    }

    private BookResponse toResponse(BookData b) {
        return new BookResponse(b.id(), b.title(), b.author(), b.isbn(), b.createdAt());
    }

    private record BookData(Long id, String title, String author, String isbn, LocalDateTime createdAt) {}
}
