package com.albercafe.rabbitmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Email
    @Column(unique = true)
    @NotNull(message = "email 은 빈 값이 들어갈 수 없습니다.")
    private String email;

    @NotNull(message = "password 는 빈 값이 들어갈 수 없습니다.")
    private String password;

    @Column(unique = true)
    private String username;

    private String address;

    @Pattern(
            regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})",
            message = "입력 예시 : 010-1234-5678, 010 1234 5678"
    )
    @Column(unique = true)
    private String phoneNumber;
}