package com.nexon.maple.config.security;

import com.nexon.maple.config.security.auth.AuthEntryPointJwt;
import com.nexon.maple.config.security.filter.JwtAuthenticationFilter;
import com.nexon.maple.config.security.filter.JwtAuthorizationFilter;
import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.userInfo.entity.GradeCode;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final CorsFilter corsFilter;
    private final AuthEntryPointJwt authEntryPointJwt;
    private final UserInfoDao userInfoDao;
    private final JwtToken jwtToken;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/favicon.ico", "/logout", "/character", "/character/**", "/user/regist", "/user/login");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
            로그인 실패 : exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            jwt 사용으로 인한 csrf 제거 : .csrf().disable()
            기본 폼 로그인 제거 : formLogin().disable()
            http basic 인증창 제거 : httpBasic().disable()
            세션 제거 : sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         */
        return http
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
            .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/user/info")
                    .authenticated()
                .antMatchers(HttpMethod.POST, "/comments")
                    .access(rolesToString(GradeCode.USER.getTitle(), GradeCode.USER.getTitle()))
            .and()
                .apply(new MyCustomDsl())
            .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
            .and()
            .build();
    }

    /*
        1. cors : jwt 토큰으로 인증절차를 처리하기에 custom cors 필터를 등록
        2. JwtAuthenticationFilter : 인증
        3. JwtAuthorizationFilter : 인가
     */
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsFilter)
                    .addFilterBefore(new JwtAuthenticationFilter(authenticationManager, jwtToken),
                            UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JwtAuthorizationFilter(authenticationManager, userInfoDao, jwtToken),
                            UsernamePasswordAuthenticationFilter.class);
        }
    }

    private String rolesToString(String... roles) {
        StringBuffer sb = new StringBuffer();

        for(String role: roles) {
            if(!sb.isEmpty()) {
                sb.append(" or ");
            }
            sb.append(String.format("hasRole('%s')", role));
        }
        return sb.toString();
    }
}