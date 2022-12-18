package ir.maktab.homeservice.controller.expertUser;

import ir.maktab.homeservice.dto.ExpertUserDto;
import ir.maktab.homeservice.entity.ExpertUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ExpertUserController {
    @PostMapping("/addCommentAndPoint")
     void addCommentAndPoint(@RequestBody ExpertUserDto expertUserDto);
    @GetMapping("/{orderId}")
    ResponseEntity<ExpertUser> findByOrderId(@PathVariable Long orderId);
}
