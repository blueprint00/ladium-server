package com.example.ladium.Dto;

import com.example.ladium.Domain.User_info;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String user_id;
    private String password;

    @Builder
    public UserDTO(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
    }

    public User_info toEntity(){
        return User_info.builder()
                .user_id(user_id)
                .password(password)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(user_id, password);
    }

}
