package ir.maktab.homeservice.dto;

import ir.maktab.homeservice.entity.Expert;
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
public class ExpertOffersDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private PersonType personType;
    private Double credit;
    private LocalDateTime signupDate;
    private List<OfferDto> offerDtos;

    public static ExpertOffersDto expertMappingToExpertOfferDto(Expert expert){
        return ExpertOffersDto.builder()
                .credit(expert.getCredit())
                .offerDtos(expert.getOffers().stream().map(OfferDto::offerToOfferDtoMapping).toList())
                .firstname(expert.getFirstname())
                .lastname(expert.getLastname())
                .email(expert.getEmail())
                .personType(PersonType.EXPERT)
                .signupDate(expert.getSignupDateTime())
                .id(expert.getId())
                .build();
    }
}
