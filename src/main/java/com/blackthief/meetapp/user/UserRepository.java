package com.blackthief.meetapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);

	Optional<User> findById(long id);

	List<User> findAll();
}
