package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "user_table")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class User extends Person {

    @OneToMany(mappedBy = "user")
    private List<ExpertUser> expertUsers;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @Builder
    public User(@NotEmpty(message = "Blank is not acceptable") String firstname
            , @NotEmpty(message = "Blank is not acceptable") String lastname
            , @Email(message = "email address is not valid") String email
            , @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$"
            , message = "password is not valid") String password, Double credit
            , LocalDateTime signupDateTime, List<ExpertUser> expertUsers
            , List<Order> orders, List<Transaction> transactions) {

        super(firstname, lastname, email, password, credit, signupDateTime);
        this.expertUsers = expertUsers;
        this.orders = orders;
        this.transactions = transactions;

    }
}
