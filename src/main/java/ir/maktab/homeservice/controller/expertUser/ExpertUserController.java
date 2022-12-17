package ir.maktab.homeservice.controller.expertUser;

import ir.maktab.homeservice.dto.ExpertUserDto;
import ir.maktab.homeservice.entity.ExpertUser;

import java.util.Optional;

public interface ExpertUserController {
    void addCommentAndPoint(ExpertUserDto expertUserDto);

    Optional<ExpertUser> findById(ExpertUser expertUser);

}
