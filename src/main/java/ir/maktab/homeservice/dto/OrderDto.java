package ir.maktab.homeservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDto {
    private Long userId;
    private Long typeId;
    private Double price;
    private String description;
    private LocalDate startOfWork;
    private String address;
}
