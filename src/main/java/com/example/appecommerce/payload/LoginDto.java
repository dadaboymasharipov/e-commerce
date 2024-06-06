package com.example.appecommerce.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull(message = "Email can't be empty")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotNull(message = "Password can't be empty")
    @NotBlank(message = "Password can't be blank")
    private String password;
}
