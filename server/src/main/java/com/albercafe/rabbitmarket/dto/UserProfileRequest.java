package com.albercafe.rabbitmarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {

    @Column(unique = true)
    private String username;

    @Pattern(
            regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})",
            message = "입력 예시 : 010-1234-5678, 010 1234 5678"
    )
    @Column(unique = true)
    private String phoneNumber;

    private String address;

    private String profilePhoto;

    @ColumnDefault("0")
    private Long ratings;
}
