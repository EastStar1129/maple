package com.nexon.maple.config.security.auth;

import com.nexon.maple.userInfo.entity.UserInfo;
import com.nexon.maple.userInfo.repository.UserInfoDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserInfoDao userInfoDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.equals("")) {
            throw new UsernameNotFoundException(username);
        }
        UserInfo userInfo = userInfoDao.findByName(username);

        if(userInfo == null) {
            throw new UsernameNotFoundException(username);
        }
        return new PrincipalDetails(userInfo);
    }

}
