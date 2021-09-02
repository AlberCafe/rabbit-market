package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.dto.UserProfileRequest;
import com.albercafe.rabbitmarket.entity.User;
import com.albercafe.rabbitmarket.entity.UserProfile;
import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import com.albercafe.rabbitmarket.repository.UserProfileRepository;
import com.albercafe.rabbitmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public ResponseEntity<CustomResponse> getUserProfile(Long id) {
        CustomResponse responseBody = new CustomResponse();

        User user = userRepository.findById(id).orElseThrow(() -> new RabbitMarketException("user can't find with : " + id + " check user id !"));
        UserProfile userProfile = user.getUserProfile();

        if (authService.getCurrentUser() == null) {
            responseBody.setData(null);
            responseBody.setError("If you want to get specific user profile, you must login first !");
            return ResponseEntity.status(401).body(responseBody);
        }

        responseBody.setData(userProfile);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    @Transactional
    public ResponseEntity<CustomResponse> updateUserProfile(Long id, UserProfileRequest userProfileRequest) {
        CustomResponse responseBody = new CustomResponse();

        User user = userRepository.findById(id).orElseThrow(() -> new RabbitMarketException("user can't find with : " + id + " check user id !"));
        UserProfile userProfile = user.getUserProfile();

        if (userProfileRequest.getUsername() != null)
            userProfile.setUsername(userProfile.getUsername());

        if (userProfileRequest.getPhoneNumber() != null)
            userProfile.setPhoneNumber(userProfile.getPhoneNumber());

        if (userProfileRequest.getAddress() != null)
            userProfile.setAddress(userProfile.getAddress());

        if (userProfileRequest.getProfilePhoto() != null)
            userProfile.setProfilePhoto(userProfile.getProfilePhoto());

        if (userProfileRequest.getRatings() != null)
            userProfile.setRatings(userProfileRequest.getRatings());

        userProfileRepository.save(userProfile);

        responseBody.setData("user id : " + id + "'s profile was updated !");
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
