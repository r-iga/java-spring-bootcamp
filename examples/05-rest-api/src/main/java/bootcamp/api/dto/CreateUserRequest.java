package bootcamp.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ユーザー作成リクエストDTO
 *
 * record はコンストラクタ・getter・equals・hashCode・toString を自動生成する
 */
public record CreateUserRequest(

    @NotBlank(message = "名前は必須です")
    @Size(max = 100, message = "名前は100文字以内にしてください")
    String name,

    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレスの形式が正しくありません")
    String email,

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 8, message = "パスワードは8文字以上にしてください")
    String password

) {}
