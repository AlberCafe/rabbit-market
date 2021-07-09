package com.albercafe.rabbitmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Pattern(
            regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})",
            message = "입력 예시 : 010-1234-5678, 010 1234 5678"
    )
    @Column(unique = true)
    private String phoneNumber;

    private String address;

    // TODO: AWS S3 와 연동
    private String profile_photo;

    @ColumnDefault("0")
    private Long ratings;

    @OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY)
    private User user;
}
