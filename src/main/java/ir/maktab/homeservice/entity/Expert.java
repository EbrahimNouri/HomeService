package ir.maktab.homeservice.entity;


import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Expert extends Person {

    @Column(nullable = false)
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
    private List<Transaction> transactions;

    private Double averageScore = 0.0;

    @Lob
    private byte[] avatar;

    @Builder
    public Expert(@NotEmpty(message = "Blank is not acceptable") String firstname, @NotEmpty(message = "Blank is not acceptable") String lastname, @Email(/*groups = CustomExceptionInvalid.class,*/ message = "email address is not valid") String email, @NotEmpty String username, @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$", message = "password is not valid") String password, double credit, LocalDateTime signupDateTime, Role role, boolean enabled, Integer verificationCode, ExpertStatus expertStatus, List<ExpertTypeService> expertTypeServices, List<ExpertUser> expertUsers, List<Transaction> transactions, Double averageScore, byte[] avatar) {
        super(firstname, lastname, email, username, password, credit, signupDateTime, role, enabled, verificationCode);
        this.expertStatus = expertStatus;
        this.expertTypeServices = expertTypeServices;
        this.expertUsers = expertUsers;
        this.transactions = transactions;
        this.averageScore = averageScore;
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Expert expert = (Expert) o;
        return getId() != null && Objects.equals(getId(), expert.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
