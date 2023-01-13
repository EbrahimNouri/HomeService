package ir.maktab.homeservice.entity.base;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Person extends BaseEntity implements UserDetails {

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

    @OneToMany(mappedBy = "user")
    private List<ExpertUser> userExpert;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Transaction> userTransactions;

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @OneToMany(mappedBy = "expert"/*, fetch = FetchType.EAGER*/)
    @ToString.Exclude
    private List<ExpertTypeService> expertTypeServices;

    @OneToMany(mappedBy = "expert")
    @ToString.Exclude
    private List<Offer> offers;

    @OneToMany(mappedBy = "expert"/*, fetch = FetchType.EAGER*/)
    @ToString.Exclude
    private List<ExpertUser> expertUsers;

    @OneToMany(mappedBy = "expert")
    @ToString.Exclude
    private List<Transaction> expertTransactions;

    private Double averageScore = 0.0;

    @Lob
    private byte[] avatar;

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
        return false;
    }
}
