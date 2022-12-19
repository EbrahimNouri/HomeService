package ir.maktab.homeservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferDto {
    private Long orderId;
    private Long expertId;
    private String description;
    private Double suggestedPrice;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
}
