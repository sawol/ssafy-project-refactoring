package com.sawol.refactor.web.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
