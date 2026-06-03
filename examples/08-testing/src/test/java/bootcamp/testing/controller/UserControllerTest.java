package bootcamp.testing.controller;

import bootcamp.testing.entity.User;
import bootcamp.testing.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * コントローラー層テスト（@WebMvcTest使用）
 *
 * ポイント:
 *   @WebMvcTest — Controller + MockMvc のみをロード（DB・Serviceはモック）
 *   @MockBean   — Spring DIコンテナにモックを登録
 *   MockMvc     — HTTPリクエスト/レスポンスをシミュレート
 *
 * 実際のHTTPサーバーは起動しないため高速に動作する
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void GET_api_users_ユーザー一覧を返す() throws Exception {
        // Arrange
        var users = List.of(
            new User(1L, "田中 太郎", "tanaka@example.com", LocalDateTime.of(2024, 1, 1, 0, 0)),
            new User(2L, "佐藤 花子", "sato@example.com", LocalDateTime.of(2024, 1, 2, 0, 0))
        );
        given(userService.findAll()).willReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("田中 太郎"))
            .andExpect(jsonPath("$[1].email").value("sato@example.com"));
    }

    @Test
    void GET_api_users_id_存在する場合_ユーザーを返す() throws Exception {
        // Arrange
        var user = new User(1L, "田中 太郎", "tanaka@example.com", LocalDateTime.now());
        given(userService.findById(1L)).willReturn(user);

        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("田中 太郎"));
    }

    @Test
    void GET_api_users_id_存在しない場合_500を返す() throws Exception {
        // Arrange — ServiceがNoSuchElementExceptionをスロー
        given(userService.findById(999L))
            .willThrow(new NoSuchElementException("ユーザーが見つかりません: id=999"));

        // Act & Assert — GlobalExceptionHandlerがなければ500になる
        mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void POST_api_users_ユーザーを作成して201を返す() throws Exception {
        // Arrange
        var created = new User(3L, "山田 次郎", "yamada@example.com", LocalDateTime.now());
        given(userService.create("山田 次郎", "yamada@example.com")).willReturn(created);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name": "山田 次郎", "email": "yamada@example.com"}
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("山田 次郎"));
    }
}
