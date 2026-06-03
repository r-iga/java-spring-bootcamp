package bootcamp.testing.service;

import bootcamp.testing.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User create(String name, String email);
}
