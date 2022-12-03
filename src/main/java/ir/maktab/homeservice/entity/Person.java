package ir.maktab.homeservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    @Email(message = "email address is not valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$", message = "password is not valid")
    private String password;

    //    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp registerTime;

//    @Column(columnDefinition = "double precision default 0.0")
    private Double credit;


    @PrePersist
    void setDefaultValue() {
        registerTime = Timestamp.valueOf(LocalDateTime.now());
        credit = 0.0;
    }

}
