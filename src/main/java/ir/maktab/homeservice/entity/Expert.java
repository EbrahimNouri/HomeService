package ir.maktab.homeservice.entity;


import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Data
@ToString
public class Expert extends Person {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @OneToMany(mappedBy = "expert", fetch = FetchType.EAGER)
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

    private byte[] avatar;

    @Builder
    public Expert(Long id, String firstname, String lastname, String email, String username, String password,
                  double credit, LocalDateTime signupDateTime, Role role, boolean enabled, Integer verificationCode,
                  ExpertStatus expertStatus, List<ExpertTypeService> expertTypeServices, List<Offer> offers,
                  List<ExpertUser> expertUsers, List<Transaction> transactions, Double averageScore, byte[] avatar) {
        super(id, firstname, lastname, email, username, password, credit, signupDateTime, role, enabled, verificationCode);
        this.expertStatus = expertStatus;
        this.expertTypeServices = expertTypeServices;
        this.offers = offers;
        this.expertUsers = expertUsers;
        this.transactions = transactions;
        this.averageScore = averageScore;
        this.avatar = avatar;
    }

    public Expert() {
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
