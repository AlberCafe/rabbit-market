package com.albercafe.rabbitmarket.entity;

import com.albercafe.rabbitmarket.util.AuthProvider;
import com.albercafe.rabbitmarket.util.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String password;

    @Email
    @Column(unique = true)
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
    @Column(name = "PROVIDER_TYPE", columnDefinition = "varchar(20) default 'LOCAL'")
    private AuthProvider provider;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_TYPE", columnDefinition = "varchar(20) default 'GUEST'")
    private RoleType roleType;

    public User update(String username, String picture, AuthProvider authProvider) {
        UserProfile userProfile = this.getUserProfile();
        userProfile.setUsername(username);
        userProfile.setProfilePhoto(picture);
        this.setUserProfile(userProfile);
        this.setProvider(authProvider);

        return this;
    }
}
