package ir.maktab.homeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long userId;
    private Long typeId;
    private Double price;
    private String description;
    private LocalDate startOfWork;
    private String address;
}
