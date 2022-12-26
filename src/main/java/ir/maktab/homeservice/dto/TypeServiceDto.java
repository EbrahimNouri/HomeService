package ir.maktab.homeservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeServiceDto {
    private Long typeServiceId;
    private String name;
    private Double price;
    private Long baseServiceId;
}
