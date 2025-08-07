package com.example.pos.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID id;

    private String name;

    private String phoneNumber;

    private String imgUrl;

    private String role;
}
