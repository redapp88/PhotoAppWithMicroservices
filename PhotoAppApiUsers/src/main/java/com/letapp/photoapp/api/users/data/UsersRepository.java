package com.letapp.photoapp.api.users.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.letapp.photoapp.api.users.entities.UserEntity;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

}
