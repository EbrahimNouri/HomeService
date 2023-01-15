package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.TypeService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExpertTypeServiceDto {

    private Long expertId;
    private String firstname;
    private String lastname;
    private String email;
    private Double averageScore;
    /*
        private byte[] avatar;
    */
    private Long typeServiceId;
    private Double credit;
    private Double averageScores;
    private String typeServiceName;
    private double basePrice;
    private String basicService;
    private String description;

    public static ExpertTypeServiceDto expertTypeServiceToDtoMapper(Expert expert, TypeService typeService) {
        return new ExpertTypeServiceDto(
                expert.getId()
                , expert.getFirstname()
                , expert.getLastname()
                , expert.getEmail()
                , expert.getAverageScore()
                , typeService.getId()
                , expert.getCredit()
                , expert.getAverageScore()
                , typeService.getSubService()
                , typeService.getBasePrice()
                , typeService.getBasicService().getName()
                , typeService.getDescription()
        );
    }

}
