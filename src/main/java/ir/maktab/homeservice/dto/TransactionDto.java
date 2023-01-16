package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TransactionDto {
    private Long expertId;
    private Long userid;
    private Double transfer;
    TransactionType transactionType;
    private LocalDateTime localDateTime;

    public static TransactionDto TransactionToDto(Transaction transaction) {
        return TransactionDto.builder()
                .expertId(transaction.getExpert() == null ? null : transaction.getExpert().getId())
                .userid(transaction.getUser().getId())
                .transfer(transaction.getTransfer())
                .transactionType(transaction.getTransactionType())
                .localDateTime(transaction.getLocalDateTime())
                .build();
    }
}
