package ir.maktab.homeservice.controller.expertUser;

import ir.maktab.homeservice.dto.ExpertUserDto;
import ir.maktab.homeservice.entity.ExpertUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ExpertUserController {
    void addCommentAndPoint(ExpertUserDto expertUserDto);

    @GetMapping("/{orderId}")
    public ResponseEntity<ExpertUser> findByOrderId(@PathVariable Long orderId);
}
