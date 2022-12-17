package ir.maktab.homeservice.controller.expert;


import ir.maktab.homeservice.dto.PersonChangePasswordDto;
import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.service.expert.ExpertService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/expert")
public class ExpertControllerImpl implements ExpertController {
    private final ExpertRepository expertRepository;

    private ExpertService expertService;


    @PostMapping("/regExpert")
    @Override
    public void registerExpert(@RequestBody @Valid PersonRegisterDto personRegisterDto) {
        Expert temp = Expert.builder()
                .expertStatus(ExpertStatus.NEW)
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .credit(0)
                .averageScores(0.0)
                .build();

        expertService.mainRegisterExpert(temp/*,new File("/Users/ebrahimnouri/ss.jpg"*/);


//        Expert expert = Expert.builder().firstname("testName").lastname("testLastname").email("test@email.com")
//                .password("1234QWer").build();
//        expertService.registerExpert(expert, new File("/Users/ebrahimnouri/ss.jpg"));
    }

    @PutMapping("/acceptExpert/{expertId}")
    @Override
    public void acceptExpert(@PathVariable Long expertId) {
        Optional<Expert> acceptExpert = expertService.findById(expertId);
        expertService.acceptExpert(acceptExpert.orElse(null));
//        return expertService.acceptExpert
//                (acceptExpert.map(ResponseEntity::ok).
//                        orElse();

    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Expert> findById(@PathVariable Long id) {
        return expertService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/chPass")
    @Override
    public void changePassword(@RequestBody @Valid PersonChangePasswordDto personChangePasswordDto) {
        Expert expert = expertRepository.findById(personChangePasswordDto.getId())
                .orElseThrow(()-> new CustomExceptionNotFind("expert not found"));
        expertService.changePassword(expert, personChangePasswordDto.getPassword());
    }

}
