package com.albercafe.rabbitmarket.repository;

import com.albercafe.rabbitmarket.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
