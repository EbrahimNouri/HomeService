package ir.maktab.homeservice.dto;

import lombok.Data;

@Data
public class ExpertUserDto {

    private Long expId;

    private Long userId;

    private Long orderId;

    private Double point;

    private String comment;

}
