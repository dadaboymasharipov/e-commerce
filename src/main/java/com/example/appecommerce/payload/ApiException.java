package com.example.appecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiException {

    private String error;//Class of the exception

    private String message;

    private long status;

    private Date time;//When this exception occurred

}
