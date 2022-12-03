package ir.maktab.homeservice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;


@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
public class Expert extends Person implements Serializable {


    @Column(nullable = false)
    private ExpertStatus expertStatus;

    @ManyToOne
    @ToString.Exclude
    private TypeService typeService;

    @OneToMany(mappedBy = "expert")
    @ToString.Exclude
    private List<ExpertUser> expertUsers;
}
