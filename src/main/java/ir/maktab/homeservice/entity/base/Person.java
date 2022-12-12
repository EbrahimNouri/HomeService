package ir.maktab.homeservice.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Person extends BaseEntity{

    @NotEmpty(message = "Blank is not acceptable")
    private String firstname;

    @NotEmpty(message = "Blank is not acceptable")
    private String lastname;

    @Email(message = "email address is not valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$", message = "password is not valid")
    private String password;

    @ColumnDefault("0.0")
    private Double credit;

    @CreationTimestamp
    @Column(name = "signup_date_time")
    private LocalDateTime signupDateTime;

}
