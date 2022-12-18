package ir.maktab.homeservice.dto;

import lombok.Data;

@Data
public class PaymentPriceChangeDto {
    private Long typeServiceId;
    private Double price;
}
