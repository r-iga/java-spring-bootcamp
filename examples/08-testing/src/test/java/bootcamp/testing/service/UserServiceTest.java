package bootcamp.testing.service;

import bootcamp.testing.entity.User;
import bootcamp.testing.mapper.UserMapper;
import bootcamp.testing.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/**
 * サービス層のユニットテスト（Mockito使用）
 *
 * ポイント:
 *   @ExtendWith(MockitoExtension.class) — Mockitoの拡張を有効化
 *   @Mock    — モックオブジェクトを生成（UserMapperの呼び出しを制御）
 *   @InjectMocks — テスト対象に@Mockを自動注入
 *
 * DBへのアクセスは発生しないため高速に動作する
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAll_ユーザー一覧を返す() {
        // Arrange — UserMapperが2件返すようにスタブ設定
        var users = List.of(
            new User(1L, "田中 太郎", "tanaka@example.com", LocalDateTime.now()),
            new User(2L, "佐藤 花子", "sato@example.com", LocalDateTime.now())
        );
        given(userMapper.findAll()).willReturn(users);

        // Act
        var result = userService.findAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("田中 太郎");

        // UserMapperが1回呼ばれたことを検証
        then(userMapper).should(times(1)).findAll();
    }

    @Test
    void findById_存在するIDの場合_ユーザーを返す() {
        // Arrange
        var user = new User(1L, "田中 太郎", "tanaka@example.com", LocalDateTime.now());
        given(userMapper.findById(1L)).willReturn(Optional.of(user));

        // Act
        var result = userService.findById(1L);

        // Assert
        assertThat(result.getName()).isEqualTo("田中 太郎");
    }

    @Test
    void findById_存在しないIDの場合_例外をスロー() {
        // Arrange
        given(userMapper.findById(999L)).willReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.findById(999L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessageContaining("999");
    }

    @Test
    void create_新規ユーザーを作成する() {
        // Arrange — insertが呼ばれたらuser.idをセットする副作用をシミュレート
        given(userMapper.insert(any(User.class))).willAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(3L);
            return 1;
        });

        // Act
        var result = userService.create("山田 次郎", "yamada@example.com");

        // Assert
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getName()).isEqualTo("山田 次郎");
        then(userMapper).should().insert(any(User.class));
    }
}
