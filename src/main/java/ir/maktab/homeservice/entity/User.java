package ir.maktab.homeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@Table(name = "users")
public class User extends Person {
    @Builder
    public User(Long id, String firstname, String lastname, String email, String password, Timestamp registerTime) {
        super(id, firstname, lastname, email, password, registerTime);
    }
}
