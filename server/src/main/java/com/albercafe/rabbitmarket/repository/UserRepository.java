package com.albercafe.rabbitmarket.repository;

import com.albercafe.rabbitmarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {
}
