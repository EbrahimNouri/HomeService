package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


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
            , @Email(/*groups = CustomExceptionInvalid.class,*/ message = "email address is not valid") String email
            , @NotEmpty String username
            , @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$"
            , message = "password is not valid") String password
            , double credit
            , LocalDateTime signupDateTime
            , Role role
            , List<ExpertUser> expertUsers
            , List<Order> orders
            , List<Transaction> transactions) {
        super(firstname, lastname, email, username, password, credit, signupDateTime, role);
        this.expertUsers = expertUsers;
        this.orders = orders;
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
