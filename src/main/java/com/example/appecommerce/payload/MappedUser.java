package com.example.appecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MappedUser {

    private String fullName;

    private String email;

    private String mobileNumber;

    private String roleName;

    private Date joinedDate;

    private String city;

    private String district;

    private String street;

}
