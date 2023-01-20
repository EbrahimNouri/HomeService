package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long userId;
    private Long expertId;
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
                .expertId(Objects.requireNonNull(order.getOffers().stream().filter(Offer::isChoose).findFirst()
                        .orElse(null)).getExpert().getId())
                .startOfWork(order.getStartOfWork())
                .address(order.getAddress())
                .build();
    }

    public static Order getOrderForRegister(OrderDto orderDto, User user) {
        return Order.builder()
                .address(orderDto.getAddress())
                .description(orderDto.getDescription())
                .user(user)
                .typeService(TypeService.builder().id(orderDto.typeId).build())
                .startOfWork(orderDto.getStartOfWork())
                .suggestedPrice(orderDto.getPrice())
                .build();
    }
}
