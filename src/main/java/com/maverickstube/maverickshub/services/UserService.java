package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dtos.response.CreateUserResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.models.User;

public interface UserService {
    CreateUserResponse register(CreateUserRequest createUserRequest);

    User getById(Long id) throws UserNotFoundException;


    User getUserByUsername(String username) throws UserNotFoundException;
}
