package com.example.appecommerce.payload;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String fullName;

    @Email
    @Nullable
    private String email;

    boolean emailChanged;//If this is true then we send a confirmation code to that email again

    private String password;

    private String mobileNumber;

    private String city;

    private String district;

    private String street;
}
