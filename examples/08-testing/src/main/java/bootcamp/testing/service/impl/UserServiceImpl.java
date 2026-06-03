package bootcamp.testing.service.impl;

import bootcamp.testing.entity.User;
import bootcamp.testing.mapper.UserMapper;
import bootcamp.testing.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id)
            .orElseThrow(() -> new NoSuchElementException("ユーザーが見つかりません: id=" + id));
    }

    @Override
    @Transactional
    public User create(String name, String email) {
        var user = new User(null, name, email, null);
        userMapper.insert(user);
        return user;
    }
}
