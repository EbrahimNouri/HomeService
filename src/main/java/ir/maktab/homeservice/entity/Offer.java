package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class Offer extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Expert expert;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    private Double suggestedPrice;

    private LocalDateTime EndDate;

    private boolean choose;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
