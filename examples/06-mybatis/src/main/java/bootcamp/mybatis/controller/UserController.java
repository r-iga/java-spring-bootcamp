package bootcamp.mybatis.controller;

import bootcamp.mybatis.entity.User;
import bootcamp.mybatis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * ユーザーCRUD APIコントローラー（MyBatis版）
 *
 * 動作確認コマンド:
 *   # 一覧
 *   curl http://localhost:8080/api/users
 *
 *   # 1件取得
 *   curl http://localhost:8080/api/users/1
 *
 *   # 作成
 *   curl -X POST http://localhost:8080/api/users \
 *        -H "Content-Type: application/json" \
 *        -d '{"name":"山田 次郎","email":"yamada@example.com"}'
 *
 *   # 更新
 *   curl -X PUT http://localhost:8080/api/users/1 \
 *        -H "Content-Type: application/json" \
 *        -d '{"name":"田中 更新","email":"tanaka2@example.com"}'
 *
 *   # 削除
 *   curl -X DELETE http://localhost:8080/api/users/1
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody Map<String, String> body) {
        return userService.create(body.get("name"), body.get("email"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(userService.update(id, body.get("name"), body.get("email")));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
