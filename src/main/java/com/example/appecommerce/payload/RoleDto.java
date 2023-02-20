package com.example.appecommerce.payload;

import com.example.appecommerce.entity.template.Permissions;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @NotNull(message = "Please enter the name of the role")
    private String name;

    @Length(max = 500)
    private String description;

    @NotEmpty(message = "Please give permissions to this role")
    private List<Permissions> permissions;

    private Date createdAt;

    private String createdByEmail;

}
