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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TypeService typeService;

    @ManyToOne
    private User user;

    private Double suggestedPrice;

    private String description;

    private LocalDate startOfWork;

    private String address;

}
