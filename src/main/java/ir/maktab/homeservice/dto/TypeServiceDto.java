package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.TypeService;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TypeServiceDto {
    private Long typeServiceId;
    private String name;
    private Double price;
    private Long baseServiceId;
    private String baseServiceName;

    public static TypeServiceDto typeServiceToTypeServiceDto(TypeService typeService){
        return TypeServiceDto.builder()
                .typeServiceId(typeService.getId())
                .name(typeService.getSubService())
                .price(typeService.getBasePrice())
                .baseServiceName(typeService.getBasicService().getName())
                .baseServiceId(typeService.getBasicService().getId())
                .build();
    }

}
