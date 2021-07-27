package com.example.ladium.Service;

import com.example.ladium.Domain.*;
import com.example.ladium.Dto.*;
import com.example.ladium.Jwt.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final User_info_repository user_info_repository;
    private final Refresh_token_repository refresh_token_repository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;


    public UserService(User_info_repository user_info_repository, Refresh_token_repository refresh_token_repository, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder) {
        this.user_info_repository = user_info_repository;
        this.refresh_token_repository = refresh_token_repository;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User_info signup(UserDTO userDTO){
        if(userDTO.getUser_id() == null){
            throw new RuntimeException("id must be provided");
        }
        if(user_info_repository.findOneWithAuthoritiesByUser_id(userDTO.getUser_id()).orElse(null) != null){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        User_info user_info = User_info.builder()
                .user_id(userDTO.getUser_id())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        return user_info_repository.save(user_info);
    }

//    @Transactional
//    public Optional<User_info> getUserWithAuthorities(String user_id){
//        return user_info_repository.findOneWithAuthoritiesByUser_id(user_id);
//    }
//
//    @Transactional
//    public Optional<User_info> getMyUserWithAuthorities(){
//        return SecurityUtil.getCurrentUserId().flatMap(user_info_repository::findOneWithAuthoritiesByUser_id);
//    }

    @Transactional
    public TokenResponseDTO login(UserDTO userDTO){
        UsernamePasswordAuthenticationToken authenticationToken = userDTO.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenResponseDTO tokenResponseDTO = tokenProvider.createToken(authentication);

        Refresh_token refresh_token = Refresh_token.builder()
                .refresh_key(authentication.getName())
                .refresh_value(tokenResponseDTO.getRefreshToken())
                .build();

        System.out.println("key : " + refresh_token.getRefresh_key());
        System.out.println("value : " + refresh_token.getRefresh_value());

        refresh_token_repository.save(refresh_token);

        return tokenResponseDTO;
    }

    @Transactional
    public TokenResponseDTO reissue(TokenRequestDTO tokenRequestDTO){
        if(!tokenProvider.validateToken(tokenRequestDTO.getRefreshToken())){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDTO.getAccessToken());

        Refresh_token refresh_token = refresh_token_repository.findByRefresh_key(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        if(!refresh_token.getRefresh_value().equals(tokenRequestDTO.getRefreshToken())){
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        TokenResponseDTO tokenResponseDTO = tokenProvider.createToken(authentication);

        Refresh_token newRefresh_token = refresh_token.updateValue(tokenResponseDTO.getRefreshToken());
        refresh_token_repository.save(newRefresh_token);

        return tokenResponseDTO;
    }

    @Transactional
    public void logout() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            refresh_token_repository.deleteById(authentication.getName());
        } catch (Exception e){
            throw new RuntimeException("이미 로그아웃 된 사용자 입니다.");
        }
    }

    @Transactional
    public void deleteUser() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user_info_repository.deleteById(authentication.getName());
        } catch (Exception e){
            throw new RuntimeException("존재하지 않는 사용자 입니다.");
        }
    }
}
