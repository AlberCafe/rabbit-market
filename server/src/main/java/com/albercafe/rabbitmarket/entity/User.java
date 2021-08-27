package com.albercafe.rabbitmarket.entity;

import com.albercafe.rabbitmarket.util.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @NotNull(message = "password 는 빈 값이 들어갈 수 없습니다.")
    private String password;

    @Email
    @Column(unique = true)
    @NotNull(message = "email 은 빈 값이 들어갈 수 없습니다.")
    private String email;

    private Boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Product> productList;

    @OneToOne
    @JoinTable(name = "user_refresh_token",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "refresh_token_id", referencedColumnName = "id") })
    private RefreshToken refreshToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'local'")
    private AuthProvider provider;

    private String providerId;
}
