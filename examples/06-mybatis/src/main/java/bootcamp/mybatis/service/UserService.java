package bootcamp.mybatis.service;

import bootcamp.mybatis.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User create(String name, String email);
    User update(Long id, String name, String email);
    void delete(Long id);
}
