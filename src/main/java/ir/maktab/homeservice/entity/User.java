package ir.maktab.homeservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
@Table(name = "users")
public class User extends Person {
    @Builder
    public User(Long id, String firstname, String lastname, @Email(message = "email address is not valid") String email
            , @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$"
            , message = "password is not valid") String password, Timestamp registerTime, Double credit) {
        super(id, firstname, lastname, email, password, registerTime, credit);
    }

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<ExpertUser> expertUsers;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders;

    private OrderType orderType;
}
