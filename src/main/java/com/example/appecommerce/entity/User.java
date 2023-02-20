package com.example.appecommerce.entity;

import com.example.appecommerce.entity.template.AbsEntity;
import com.example.appecommerce.entity.template.Permissions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    @NotEmpty(message = "username can't be empty")
    private String fullName;
    @Column(nullable = false, unique = true)
    @Email(message = "Please enter a valid Email")
    private String email;//Used as a username of the user


    @Column(nullable = false)
    @NotEmpty(message = "password can't be empty")
    private String password;


    @ManyToOne(optional = false)
    private Role role;

    @Column(nullable = false)
    private String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,optional = false, orphanRemoval = true)
    private Address address;

    public User(String fullName, String email, String password, Role role, String mobileNumber, Address address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.mobileNumber = mobileNumber;

        address.setCreatedBy(this.getId());//We'll set its created by to current user
        this.address = address;
    }

    private String emailCode;

    private boolean AccountNonLocked = true;
    private boolean CredentialsNonExpired = true;
    private boolean AccountNonExpired = true;
    private boolean Enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permissions permission : this.role.getPermissions()) {
            authorities.add(permission::name);
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
