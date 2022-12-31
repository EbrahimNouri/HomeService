package ir.maktab.homeservice.entity;


import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import jakarta.persistence.*;
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


@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Expert extends Person implements UserDetails {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @OneToMany(mappedBy = "expert"/*, fetch = FetchType.EAGER*/)
    @ToString.Exclude
    private List<ExpertTypeService> expertTypeServices;

    @OneToMany(mappedBy = "expert"/*, fetch = FetchType.EAGER*/)
    @ToString.Exclude
    private List<ExpertUser> expertUsers;

    @OneToMany(mappedBy = "expert")
    @ToString.Exclude
    private List<Transaction> transactions;

    /*
        @ColumnDefault("0")
    */
    private Double averageScore;

    @Lob
    private byte[] avatar;

    public Expert(
            @NotEmpty(message = "Blank is not acceptable") String firstname
            , @NotEmpty(message = "Blank is not acceptable") String lastname
            , @Email(/*groups = CustomPatternInvalidException.class,*/ message = "email address is not valid") String email
            , String username, @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$"
            , message = "password is not valid") String password
            , double credit
            , LocalDateTime signupDateTime
            , ExpertStatus expertStatus
            , List<ExpertTypeService> expertTypeServices
            , List<ExpertUser> expertUsers, List<Transaction> transactions
            , Double averageScore, byte[] avatar
    ) {
        super(firstname, lastname, email, username, password, credit, signupDateTime);
        this.expertStatus = expertStatus;
        this.expertTypeServices = expertTypeServices;
        this.expertUsers = expertUsers;
        this.transactions = transactions;
        this.averageScore = averageScore;
        this.avatar = avatar;
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
