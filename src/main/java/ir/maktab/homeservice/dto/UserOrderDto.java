package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private PersonType personType;
    private Double credit;
    private LocalDateTime signupDate;
    private List<OrderDto> orderDtos;

    public static UserOrderDto userToUserOrderDtoMapper(User user){
        return UserOrderDto.builder()
                .id(user.getId())
                .orderDtos(user.getOrders().stream().map((OrderDto::orderToOrderDtoMapper)).toList())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .personType(PersonType.USER)
                .signupDate(user.getSignupDateTime())
                .credit(user.getCredit())
                .build();
    }
}
