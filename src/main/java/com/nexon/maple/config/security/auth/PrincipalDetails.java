package com.nexon.maple.config.security.auth;

import com.nexon.maple.userInfo.entity.UserInfo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/* Security가 loginProcessingUrl에 등록한 주소요청이 오면 로그인을 진행한다.
 * 로그인이 완료되면 시큐리티 session을 만드는데 Security session은 SecurityContextHolder에 저장됩니다.
 *  ** securityContextHolder란 **
 * security가 인증한 내용들을 가지고 있으며 securityContext를 포함하고 있고 현재스레드와 securityCotnext를 연결해준다.
 * *********************************************************************
 * Session Object는 Authentication Type으로 저장되어야 한다.
 * 또한 Authentication 객체안에 유저정보를 저장할 떄 유저정보는 UserDetails여야 한다.
 * 따라서 Security Session >> Authentication >> UserDetails
 *
 * */
@Data
public class PrincipalDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UserInfo userInfo; // composition

    //일반로그인
    public PrincipalDetails(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    //User 권한을 리턴하는 곳
    /*
        User Grade(등급)로 처리하고있는데 등급의 권한을 넣어주어야 하는지 체크
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> userInfo.getGradeCode());
        return authorities;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        //계정이 만료되었는가?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정이 잠금상태인가?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //계정 비밀번호가 몇일이 지났나?
        return true;
    }

    @Override
    public boolean isEnabled() {
        //계정이 활성화 되있나??
        return true;
    }
}
