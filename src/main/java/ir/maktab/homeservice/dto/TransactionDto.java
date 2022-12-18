package ir.maktab.homeservice.dto;

import lombok.Data;

@Data
public class TransactionDto {
    private Long expertId;
    private Long userid;
    private Double amount;
}
