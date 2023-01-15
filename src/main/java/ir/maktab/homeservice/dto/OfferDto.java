package ir.maktab.homeservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    private Long orderId;

    private Long expertId;

    private String description;

    private Double suggestedPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    public static OfferDto offerToOfferDtoMapping(Offer offer){
        return OfferDto.builder()
                .suggestedPrice(offer.getSuggestedPrice())
                .description(offer.getDescription())
                .expertId(offer.getExpert().getId())
                .orderId(offer.getOrder().getId())
                .startDate(offer.getStartDate())
                .endDate(offer.getEndDate())
                .build();
    }
    public static Offer offerDtoToOfferMapping(OfferDto offer, Expert expert, Order order){
        return Offer.builder()
                .suggestedPrice(offer.getSuggestedPrice())
                .description(offer.getDescription())
                .expert(expert)
                .order(order)
                .startDate(offer.getStartDate())
                .EndDate(offer.getEndDate())
                .build();
    }

}
