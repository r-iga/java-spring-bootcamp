package bootcamp.mybatis.service.impl;

import bootcamp.mybatis.entity.User;
import bootcamp.mybatis.mapper.UserMapper;
import bootcamp.mybatis.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    @Transactional
    public User create(String name, String email) {
        if (userMapper.existsByEmail(email)) {
            throw new IllegalArgumentException("このメールアドレスはすでに使用されています: " + email);
        }
        var user = new User(null, name, email, null);
        userMapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User update(Long id, String name, String email) {
        var user = userMapper.findById(id)
            .orElseThrow(() -> new NoSuchElementException("ユーザーが見つかりません: id=" + id));
        user.setName(name);
        user.setEmail(email);
        userMapper.update(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userMapper.findById(id)
            .orElseThrow(() -> new NoSuchElementException("ユーザーが見つかりません: id=" + id));
        userMapper.deleteById(id);
    }
}
