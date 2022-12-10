package ir.maktab.homeservice.entity;



import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Expert extends Person {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @OneToMany(mappedBy = "expert")
    private List<ExpertTypeService> expertTypeServices;

    @OneToMany(mappedBy = "expert")
    @ToString.Exclude
    private List<ExpertUser> expertUsers;

    @OneToMany(mappedBy = "expert")
    private List<Transaction> transactions;

    @ColumnDefault("0")
    private Double averageScore;

    @Lob
    private byte[] avatar;

    @Builder
    public Expert(@NotEmpty(message = "Blank is not acceptable") String firstname
            , @NotEmpty(message = "Blank is not acceptable") String lastname
            , @Email(message = "email address is not valid") String email
            , @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,128}$"
            , message = "password is not valid") String password, Double credit
            , LocalDateTime signupDateTime, ExpertStatus expertStatus
            , List<ExpertTypeService> expertTypeServices
            , List<ExpertUser> expertUsers
            , List<Transaction> transactions
            , Double averageScores
            , byte[] avatar) {

        super(firstname, lastname, email, password, credit, signupDateTime);
        this.expertStatus = expertStatus;
        this.expertTypeServices = expertTypeServices;
        this.expertUsers = expertUsers;
        this.transactions = transactions;
        this.averageScore = averageScores;
        this.avatar = avatar;

    }
}