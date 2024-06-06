package com.example.appecommerce.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Full name can't be blank")
    @NotNull(message = "Full name can't be empty")
    private String fullName;

    @Email(message = "Please enter a valid Email")
    @NotNull(message = "Email can't be empty")
    private String email;

    @NotNull(message = "Password can't be empty")
    @NotBlank(message = "Password can't be blank")
    private String password;

    @NotNull(message = "Please enter your mobile number")
    @Size(min = 9, max = 13, message = "Your number should be between 9 and 12 digits")
    private String mobileNumber;//+998 90 123 45 67

    @NotNull(message = "Please enter your city")
    private String city;

    @NotNull(message = "Please enter your district")
    private String district;

    @NotNull(message = "Please enter your street and home number")
    private String street;

}
