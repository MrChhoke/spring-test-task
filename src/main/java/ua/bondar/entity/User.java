package ua.bondar.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@ApiModel(value = "User", description = "Entity for user")
public class User implements UserDetails {

    @ApiModelProperty(value = "Unique identifier of user",
            example = "1",
            dataType = "Integer",
            name = "id")
    private int id;
    @ApiModelProperty(value = "The name of user. It is also unique",
            example = "Vlad",
            dataType = "String",
            name = "name")
    private String username;
    @ApiModelProperty(value = "Encrypted password of user",
            example = "$2a$10$qxXuwZeeHO9/Bc48tSXQoe4epv5Y3PRgfTS0qCgveyyOuLlrKhuiW",
            dataType = "String",
            name = "password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // I only have one role at the moment ("USER")
        return List.of(
                new SimpleGrantedAuthority("USER")
        );
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
