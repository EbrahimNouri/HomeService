package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import ir.maktab.homeservice.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Admin extends BaseEntity implements UserDetails {

    @Column(unique = true)
    private String name;

    @Email(message = "email address is not valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$", message = "password is not valid")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Admin(Long id, String name, String email, String username, String password, Role role) {
        super(id);
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Admin() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Admin admin = (Admin) o;
        return getId() != null && Objects.equals(getId(), admin.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
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
