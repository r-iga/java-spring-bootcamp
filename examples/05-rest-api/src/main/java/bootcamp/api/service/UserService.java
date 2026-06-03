package bootcamp.api.service;

import bootcamp.api.dto.CreateUserRequest;
import bootcamp.api.dto.UpdateUserRequest;
import bootcamp.api.dto.UserResponse;

import java.util.List;

/** ユーザー管理サービスのインターフェース */
public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(Long id);
    UserResponse create(CreateUserRequest request);
    UserResponse update(Long id, UpdateUserRequest request);
    void delete(Long id);
}
