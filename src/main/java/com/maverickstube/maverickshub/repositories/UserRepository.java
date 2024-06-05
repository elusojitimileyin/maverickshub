package com.maverickstube.maverickshub.repositories;

import com.maverickstube.maverickshub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
