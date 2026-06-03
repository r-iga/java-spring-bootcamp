package bootcamp.api.controller;

import bootcamp.api.dto.CreateUserRequest;
import bootcamp.api.dto.UpdateUserRequest;
import bootcamp.api.dto.UserResponse;
import bootcamp.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ユーザーCRUD REST APIコントローラー
 *
 * エンドポイント一覧:
 *   GET    /api/users       — 一覧取得
 *   GET    /api/users/{id}  — 1件取得
 *   POST   /api/users       — 新規作成
 *   PUT    /api/users/{id}  — 更新
 *   DELETE /api/users/{id}  — 削除
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id,
                               @Valid @RequestBody UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
