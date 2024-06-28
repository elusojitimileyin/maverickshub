package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dtos.response.CreateUserResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.models.User;
import com.maverickstube.maverickshub.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class MavericksHubService implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public MavericksHubService(UserRepository userRepository,
                               ModelMapper modelMapper,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserResponse register(CreateUserRequest createUserRequest) {
        User newUser = modelMapper.map(createUserRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        User savedUser = userRepository.save(newUser);
        var response = modelMapper.map(savedUser, CreateUserResponse.class);
        response.setMessage("user registered successfully");
        return response;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->
                new RuntimeException
                        (String.format("user not found", id)));
    }

    public User getUserByUsername(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new UserNotFoundException("user not found"));
        return user;
    }
}
