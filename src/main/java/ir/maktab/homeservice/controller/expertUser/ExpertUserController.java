package ir.maktab.homeservice.controller.expertUser;

import ir.maktab.homeservice.entity.ExpertUser;

import java.util.Optional;

public interface ExpertUserController {
    void addCommentAndPoint(ExpertUser expertUser);

    Optional<ExpertUser> findById(ExpertUser expertUser);

}
