package com.example.appecommerce.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockUserDto {
    @NotNull(message = "Please enter id of the user to be blocked")
    private UUID id;

    @NotNull(message = "Please give a reason to block")
    private String reason;
}
