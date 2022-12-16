package ir.maktab.homeservice.controller.expert;

import ir.maktab.homeservice.dto.PersonChangePasswordDto;
import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.Expert;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ExpertController {


    @PostMapping("/mainRegExpert")
    void registerExpert(@RequestBody @Valid PersonRegisterDto personRegisterDto);

    @PutMapping("/acceptExpert/{expertId}")
    void acceptExpert(@PathVariable Long expertId);

    @GetMapping("/{id}")
    ResponseEntity<Expert> findById(@PathVariable Long id);

    @PutMapping("/chPass")
    void changePassword(@RequestBody PersonChangePasswordDto personChangePasswordDto);

}
