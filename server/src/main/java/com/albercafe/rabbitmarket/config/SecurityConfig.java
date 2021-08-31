package com.albercafe.rabbitmarket.config;

import com.albercafe.rabbitmarket.security.JWTAuthenticationFilter;
import com.albercafe.rabbitmarket.security.RestAuthenticationEntryPoint;
import com.albercafe.rabbitmarket.security.oauth2.CustomOAuth2Provider;
import com.albercafe.rabbitmarket.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.albercafe.rabbitmarket.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.albercafe.rabbitmarket.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.albercafe.rabbitmarket.service.CustomOAuth2UserService;
import com.albercafe.rabbitmarket.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers("/api/auth/**", "/api/oauth2/**", "/oauth2/**")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/categories/**")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/products/**")
                        .permitAll()
                    .antMatchers(
                            HttpMethod.GET,
                            "/v2/api-docs",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/swagger-ui/**",
                            "/swagger-ui/index.html",
                            "/webjars/**"
                    )
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                        .and()
                    .oauth2Login()
                        .authorizationEndpoint()
                            .baseUri("/oauth2/authorize")
                            .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
                            .and()
                        .redirectionEndpoint()
                            .baseUri("/oauth2/callback/*")
                            .and()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService)
                            .and()
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler);

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.formLogin()
                .usernameParameter("email");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,
            @Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoClientId,
            @Value("${spring.security.oauth2.client.registration.kakao.client-secret}") String kakaoClientSecret,
            @Value("${spring.security.oauth2.client.registration.naver.client-id}") String naverClientId,
            @Value("${spring.security.oauth2.client.registration.naver.client-secret}") String naverClientSecret
    ) {
        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration()
                .keySet().stream().map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                    .clientId(kakaoClientId)
                    .clientSecret(kakaoClientSecret)
                    .jwkSetUri("temp")
                    .build());

        registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                    .clientId(naverClientId)
                    .clientSecret(naverClientSecret)
                    .jwkSetUri("temp")
                    .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if ("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");

            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        return null;
    }
}
