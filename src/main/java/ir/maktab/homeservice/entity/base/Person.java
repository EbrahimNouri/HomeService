package ir.maktab.homeservice.entity.base;

import ir.maktab.homeservice.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
public abstract class Person extends BaseEntity implements UserDetails {

    @NotEmpty(message = "Blank is not acceptable")
    private String firstname;

    @NotEmpty(message = "Blank is not acceptable")
    private String lastname;

    @Email(/*groups = CustomExceptionInvalid.class,*/ message = "email address is not valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$", message = "password is not valid")
    private String password;

    private double credit = 0.0;

    @CreationTimestamp
    @Column(name = "signup_date_time")
    private LocalDateTime signupDateTime;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "verification_code", length = 5)
    private Integer verificationCode;

    public Person(Long id, String firstname, String lastname, String email, String username, String password
            , double credit, LocalDateTime signupDateTime, Role role, boolean enabled, Integer verificationCode) {
        super(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.credit = credit;
        this.signupDateTime = signupDateTime;
        this.role = role;
        this.enabled = enabled;
        this.verificationCode = verificationCode;
    }

    public Person() {
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
        return enabled;
    }
}
