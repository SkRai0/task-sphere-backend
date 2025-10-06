package com.iamskrai.task_sphere.user;

import com.iamskrai.task_sphere.user.dto.UserCreateRequest;
import com.iamskrai.task_sphere.user.dto.UserResponse;
import com.iamskrai.task_sphere.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    private User mapToEntity(UserCreateRequest request) {
        return new User(request.getName(), request.getEmail());
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Create a User
    public UserResponse createUser(UserCreateRequest request) {
        User user = mapToEntity(request);
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse updateUser(Long id, UserCreateRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        return mapToResponse(userRepository.save(existing));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
