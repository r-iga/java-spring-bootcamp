package bootcamp.mybatis.mapper;

import bootcamp.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * MyBatis Mapper インターフェース
 *
 * @Mapper を付けることで Spring が自動的に実装クラスを生成してDIコンテナに登録する
 * SQL は resources/mapper/UserMapper.xml に記述
 */
@Mapper
public interface UserMapper {

    Optional<User> findById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    /** @return 挿入した行数（1が正常）。user.id に生成されたIDが設定される */
    int insert(User user);

    /** @return 更新した行数 */
    int update(User user);

    /** @return 削除した行数 */
    int deleteById(Long id);
}
