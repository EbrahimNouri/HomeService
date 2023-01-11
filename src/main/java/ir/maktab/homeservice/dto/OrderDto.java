package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.OrderType;
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
    private OrderType orderType;

    public static OrderDto orderToOrderDtoMapper(Order order){
        return OrderDto.builder()
                .description(order.getDescription())
                .price(order.getSuggestedPrice())
                .orderType(order.getOrderType())
                .userId(order.getUser().getId())
                .startOfWork(order.getStartOfWork())
                .address(order.getAddress())
                .build();
    }


}
