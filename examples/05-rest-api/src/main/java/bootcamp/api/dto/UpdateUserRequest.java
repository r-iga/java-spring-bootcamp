package bootcamp.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/** ユーザー更新リクエストDTO — すべてのフィールドはオプション */
public record UpdateUserRequest(

    @Size(max = 100, message = "名前は100文字以内にしてください")
    String name,

    @Email(message = "メールアドレスの形式が正しくありません")
    String email

) {}
