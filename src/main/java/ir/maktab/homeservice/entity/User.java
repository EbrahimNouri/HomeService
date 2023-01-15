package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity(name = "user_table")
@Getter
@Setter
public class User extends Person {

    @OneToMany(mappedBy = "user")
    private List<ExpertUser> expertUsers;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @Builder
    public User(Long id, String firstname, String lastname, String email, String username, String password,
                double credit, LocalDateTime signupDateTime, Role role, boolean enabled, Integer verificationCode,
                List<ExpertUser> expertUsers, List<Order> orders, List<Transaction> transactions) {
        super(id, firstname, lastname, email, username, password, credit, signupDateTime, role, enabled, verificationCode);
        this.expertUsers = expertUsers;
        this.orders = orders;
        this.transactions = transactions;
    }

    public User() {
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
