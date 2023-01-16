package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.ExpertUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpertUserDto {
    private Long expId;
    private Long orderId;
    private Double point;
    private String comment;


    public static ExpertUserDto expertUserDtoMapper(ExpertUser expertUser) {
        return ExpertUserDto.builder()
                .expId(expertUser.getExpert().getId())
                .orderId(expertUser.getOrder().getId())
                .point(expertUser.getPoint())
                .comment(expertUser.getComment() == null ? null : expertUser.getComment())
                .build();
    }
}
