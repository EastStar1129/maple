package com.nexon.maple.config.security;

import com.nexon.maple.config.security.auth.AuthEntryPointJwt;
import com.nexon.maple.config.security.filter.JwtAuthenticationFilter;
import com.nexon.maple.config.security.filter.JwtAuthorizationFilter;
import com.nexon.maple.config.security.jwt.JwtToken;
import com.nexon.maple.login.service.LoginService;
import com.nexon.maple.userInfo.type.GradeCode;
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
    private final JwtToken jwtToken;
    private final LoginService loginService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/resources/js/**", "/resources/css/**", "/resources/image/**",
                        "/favicon.ico", "/", "/index",
                        "/character", "/character/**",
                        "/user/regist", "/user/login");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
            ????????? ?????? : exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            jwt ???????????? ?????? csrf ?????? : .csrf().disable()
            ?????? ??? ????????? ?????? : formLogin().disable()
            http basic ????????? ?????? : httpBasic().disable()
            ?????? ?????? : sessionCreationPolicy(SessionCreationPolicy.STATELESS)
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
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies(jwtToken.getAccessTokenName())
                .invalidateHttpSession(true).deleteCookies(jwtToken.getRefreshTokenName())
                .invalidateHttpSession(true).deleteCookies(jwtToken.getTokenFlagName())
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .and()
                .authorizeRequests()
                .antMatchers("/user/info")
                    .authenticated()
                .antMatchers(HttpMethod.POST, "/comments")
                    .access(rolesToString(GradeCode.ROLE_ADMIN.getTitle(), GradeCode.ROLE_USER.getTitle()))
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
        1. cors : jwt ???????????? ??????????????? ??????????????? custom cors ????????? ??????
        2. JwtAuthenticationFilter : ??????
        3. JwtAuthorizationFilter : ??????
     */
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsFilter)
                    .addFilterBefore(new JwtAuthenticationFilter(authenticationManager, jwtToken, loginService),
                            UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JwtAuthorizationFilter(authenticationManager, jwtToken, loginService),
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