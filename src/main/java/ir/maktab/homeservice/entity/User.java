package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity(name = "user_table")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class User extends Person implements UserDetails {

    @OneToMany(mappedBy = "user")
    private List<ExpertUser> expertUsers;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @Column(unique = true)
    private String username;

    public User(
            @NotEmpty(message = "Blank is not acceptable") String firstname
            , @NotEmpty(message = "Blank is not acceptable") String lastname
            , @Email(/*groups = CustomPatternInvalidException.class,*/ message = "email address is not valid") String email
            , String username, @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$"
            , message = "password is not valid") String password
            , double credit
            , LocalDateTime signupDateTime
            , List<ExpertUser> expertUsers
            , List<Order> orders
            , List<Transaction> transactions
            , String username1
    ) {
        super(firstname, lastname, email, username, password, credit, signupDateTime);
        this.expertUsers = expertUsers;
        this.orders = orders;
        this.transactions = transactions;
        this.username = username1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(expertUsers, user.expertUsers) && Objects.equals(orders, user.orders) && Objects.equals(transactions, user.transactions) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expertUsers, orders, transactions, username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
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
