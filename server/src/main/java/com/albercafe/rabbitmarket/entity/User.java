package com.albercafe.rabbitmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    @NotBlank(message = "password 는 빈 값이 들어갈 수 없습니다.")
    private String password;

    @Email
    @NotEmpty(message = "email 은 빈 값이 들어갈 수 없습니다.")
    private String email;

    @Pattern(
            regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})",
            message = "010-1234-5678 로 입력하거나 010 1234 5678 로 입력해주세요"
    )
    private String phoneNumber;

    // 이메일 혹은 핸드폰을 통해 인증된 유저인지 파악
    private boolean enabled;

    // TODO: 별도의 엔티티로 차후에 분리될 수 있음
    private String address;

    // TODO: 프로필 사진은 별도의 엔티티 선언
}
