package com.gitlab.users.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


public class DtoUserInformation implements Serializable {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Email
    @NotBlank(message = "用户账号不能为空")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    private String password;



}
