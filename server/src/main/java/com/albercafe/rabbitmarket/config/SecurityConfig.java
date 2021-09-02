package com.albercafe.rabbitmarket.config;

import com.albercafe.rabbitmarket.security.JWTAuthenticationFilter;
import com.albercafe.rabbitmarket.service.CustomOAuth2UserService;
import com.albercafe.rabbitmarket.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

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
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/api/auth/**", "/api/oauth2/**", "/oauth2/**", "/login/oauth2/**")
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
                        .defaultSuccessUrl("/loginSuccess")
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.formLogin().usernameParameter("email");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
