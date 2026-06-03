package bootcamp.api.exception;

/** 指定したユーザーが見つからない場合にスローする例外 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("ユーザーが見つかりません: id=" + id);
    }
}
