package ir.maktab.homeservice.entity;

import ir.maktab.homeservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Offer offer = (Offer) o;
        return getId() != null && Objects.equals(getId(), offer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
