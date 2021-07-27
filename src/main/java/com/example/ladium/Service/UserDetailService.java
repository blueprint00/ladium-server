package com.example.ladium.Service;

import com.example.ladium.Domain.User_info;
import com.example.ladium.Domain.User_info_repository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component("userDetailService")
public class UserDetailService implements UserDetailsService {
    private final User_info_repository user_info_repository;

    public UserDetailService(User_info_repository user_info_repository) {
        this.user_info_repository = user_info_repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String user_id) {
        return user_info_repository.findOneWithAuthoritiesByUser_id(user_id)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(user_id + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(User_info user_info) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user_info.getUser_id());
        return new User(
                user_info.getUser_id(),
                user_info.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
