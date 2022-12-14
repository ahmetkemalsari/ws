package com.hoaxify.ws.entity;


import com.hoaxify.ws.manager.UniqueUsername;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "{hoaxify.constraints.userName.NotNull.message}")
    @UniqueUsername(message = "{hoaxify.constraints.username.UniqueUsername.message}")
    @Size(min = 4,max = 255)
    private String username;

    @NotNull(message = "{hoaxify.constraints.displayName.NotNull.message}")
    @Size(min = 4,max = 255)
    private String displayName;

    @NotNull(message = "{hoaxify.constraints.password.NotNull.message}")
    @Size(min = 8,max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{hoaxify.constraints.password.Pattern.message}")
    private String password;

    private String image;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_user");
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
