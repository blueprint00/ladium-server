package com.example.ladium.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {
    private String accessToken;
    private String refreshToken;
}
