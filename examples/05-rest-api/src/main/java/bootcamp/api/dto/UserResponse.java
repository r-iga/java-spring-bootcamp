package bootcamp.api.dto;

import java.time.LocalDateTime;

/** ユーザーレスポンスDTO — パスワードなど内部情報は含まない */
public record UserResponse(
    Long id,
    String name,
    String email,
    LocalDateTime createdAt
) {}
