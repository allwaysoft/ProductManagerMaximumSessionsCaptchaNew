package com.example;

import lombok.Data;

@Data
public class UserResetPasswordDTO {

    private int id;
    private String username;
    @ValidPassword
    private String password;

}
