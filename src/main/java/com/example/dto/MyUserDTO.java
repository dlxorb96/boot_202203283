package com.example.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUserDTO extends User {

    private String username = null;
    private String userpassword = null;
    private String userphone = null;
    private String name = null;

    public MyUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities,
            String userphone, String name) {
        super(username, password, authorities);
        this.username = username;
        this.userpassword = password;
        this.userphone = userphone; // 연락처
        this.name = name; // 이름
        // System.out.println(username, userpassword, userphone, name);
    }
    // System.out.println(username, userpassword, userphone, name);
}
