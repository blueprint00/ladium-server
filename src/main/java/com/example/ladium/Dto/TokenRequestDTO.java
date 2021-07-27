package com.example.ladium.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDTO {
    private String accessToken;
    private String refreshToken;
}
