package bootcamp.testing.mapper;

import bootcamp.testing.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findById(Long id);
    List<User> findAll();
    int insert(User user);
    int deleteById(Long id);
}
