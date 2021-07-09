package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.exception.UserProfileNotFoundException;
import com.albercafe.rabbitmarket.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;

    @Transactional(readOnly = true)
    public UserProfile getUserProfile(Long id) {
        return userProfileRepository.findById(id).orElseThrow(() -> new UserProfileNotFoundException("이 아이디로 프로필을 찾을 수 없음" + id));
    }
}
