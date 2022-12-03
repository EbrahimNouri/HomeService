package ir.maktab.homeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Expert expert;

    @Column(nullable = false)
    private LocalDate startDate;

    private Double suggestedPrice;
    //todo check in service be after start
    private LocalDate EndDate;

}
