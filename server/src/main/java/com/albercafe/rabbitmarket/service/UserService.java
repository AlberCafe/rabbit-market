package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserProfileRepository userProfileRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public ResponseEntity<CustomResponse> getUserProfile(Long id) {
        CustomResponse responseBody = new CustomResponse();

        Optional<UserProfile> userProfile = userProfileRepository.findById(id);

        if (userProfile.isEmpty()) {
            responseBody.setData(null);
            responseBody.setError("Wrong user id ");
            return ResponseEntity.status(400).body(responseBody);
        }

        if (authService.getCurrentUser() == null) {
            responseBody.setData(null);
            responseBody.setError("If you want to get specific user profile, you must login first !");
            return ResponseEntity.status(401).body(responseBody);
        }

        responseBody.setData(userProfile.get());
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
