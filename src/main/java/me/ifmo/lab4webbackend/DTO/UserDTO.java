package me.ifmo.lab4webbackend.DTO;

import lombok.*;

@Data
public class UserDTO {
    @NonNull
    private final String username;

    @NonNull
    private final String password;
}
