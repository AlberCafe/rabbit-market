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

    @NotBlank(message = "username 은 빈 값이 들어갈 수 없습니다.")
    private String username;

    @NotBlank(message = "password 는 빈 값이 들어갈 수 없습니다.")
    private String password;

    @Email
    @NotEmpty(message = "email 은 빈 값이 들어갈 수 없습니다.")
    private String email;

    // TODO: 정규식이 차후에 변경될 수 있음
    @NotNull
    @Pattern(
            regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})",
            message = "01로 시작해야하고, 두번째 자리가 3 ~ 4자리 숫자, 마지막 자리가 4자리 숫자이어야 합니다."
    )
    private String phoneNumber;

    // 이메일 혹은 핸드폰을 통해 인증된 유저인지 파악
    private boolean enabled;

    // TODO: 별도의 엔티티로 차후에 분리될 수 있음
    private String address;

    // TODO: 프로필 사진은 별도의 엔티티 선언
}
