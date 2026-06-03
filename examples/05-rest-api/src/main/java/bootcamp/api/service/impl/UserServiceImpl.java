package bootcamp.api.service.impl;

import bootcamp.api.dto.CreateUserRequest;
import bootcamp.api.dto.UpdateUserRequest;
import bootcamp.api.dto.UserResponse;
import bootcamp.api.exception.UserNotFoundException;
import bootcamp.api.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * インメモリ実装 — DBなしで動作確認できる
 *
 * Chapter 6 (MyBatis) では、このクラスをDB版の実装に差し替える
 */
@Service
public class UserServiceImpl implements UserService {

    // スレッドセーフなインメモリストレージ
    private final Map<Long, UserData> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    // アプリ起動時にサンプルデータを投入
    public UserServiceImpl() {
        createInternal("田中 太郎", "tanaka@example.com", "password01");
        createInternal("佐藤 花子", "sato@example.com", "password02");
    }

    @Override
    public List<UserResponse> findAll() {
        return store.values().stream()
            .sorted((a, b) -> Long.compare(a.id(), b.id()))
            .map(this::toResponse)
            .toList();
    }

    @Override
    public UserResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        return toResponse(createInternal(request.name(), request.email(), request.password()));
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        var existing = getOrThrow(id);
        var updated = new UserData(
            existing.id(),
            request.name() != null ? request.name() : existing.name(),
            request.email() != null ? request.email() : existing.email(),
            existing.createdAt()
        );
        store.put(id, updated);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        getOrThrow(id);
        store.remove(id);
    }

    // ─── プライベートヘルパー ────────────────────────────────

    private UserData getOrThrow(Long id) {
        var user = store.get(id);
        if (user == null) throw new UserNotFoundException(id);
        return user;
    }

    private UserData createInternal(String name, String email, String password) {
        long id = idSequence.getAndIncrement();
        var user = new UserData(id, name, email, LocalDateTime.now());
        store.put(id, user);
        return user;
    }

    private UserResponse toResponse(UserData data) {
        return new UserResponse(data.id(), data.name(), data.email(), data.createdAt());
    }

    // ─── 内部データ表現（record）─────────────────────────────
    private record UserData(Long id, String name, String email, LocalDateTime createdAt) {}
}
