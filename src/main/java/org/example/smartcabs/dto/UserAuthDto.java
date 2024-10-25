package org.example.smartcabs.dto;

import lombok.Data;

@Data
public class UserAuthDto {
    private String accessToken ;
    private String tokenType ;
    private Long userId;
    private String userRole;

    public UserAuthDto(String accessToken , String tokenType, Long userId, String userRole) {
        this.tokenType = tokenType ;
        this.accessToken = accessToken;
        this.userId = userId;
        this.userRole = userRole;
    }
}
