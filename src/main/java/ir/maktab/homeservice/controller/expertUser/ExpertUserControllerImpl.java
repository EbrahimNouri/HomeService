package ir.maktab.homeservice.controller.expertUser;


import ir.maktab.homeservice.entity.ExpertUser;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/expertUser")
public class ExpertUserControllerImpl implements ExpertUserController {


    @Override
    public void addCommentAndPoint(ExpertUser expertUser) {

    }

    @Override
    public Optional<ExpertUser> findById(ExpertUser expertUser) {
        return Optional.empty();
    }
}