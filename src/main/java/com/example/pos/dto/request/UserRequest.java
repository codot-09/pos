package com.example.pos.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String phone;
    private String name;
    private String password;
}
