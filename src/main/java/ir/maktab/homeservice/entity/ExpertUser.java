package ir.maktab.homeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@IdClass(ExpertUserId.class)
public class ExpertUser extends ExpertUserId {
    @ManyToOne
    @Id
    private Expert expert;

    @ManyToOne
    @Id
    private User user;

    @Id
    private LocalDate localDate = LocalDate.now();

    private float point;

    private String comment;


}
