package com.albercafe.rabbitmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email
    @Column(unique = true)
    @NotNull(message = "email 은 빈 값이 들어갈 수 없습니다.")
    private String email;

    @NotNull(message = "password 는 빈 값이 들어갈 수 없습니다.")
    private String password;
}
