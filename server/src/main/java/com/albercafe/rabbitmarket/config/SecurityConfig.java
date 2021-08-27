package com.albercafe.rabbitmarket.config;

import com.albercafe.rabbitmarket.security.JWTAuthenticationFilter;
import com.albercafe.rabbitmarket.security.RestAuthenticationEntryPoint;
import com.albercafe.rabbitmarket.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.albercafe.rabbitmarket.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.albercafe.rabbitmarket.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.albercafe.rabbitmarket.service.CustomOAuth2UserService;
import com.albercafe.rabbitmarket.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                    .antMatchers("/api/auth/**", "/oauth2/**")
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

}
