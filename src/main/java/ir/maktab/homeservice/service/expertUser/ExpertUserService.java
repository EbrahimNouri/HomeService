package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.ExpertUser;

import java.util.Optional;

public interface ExpertUserService {

    void addCommentAndPoint(ExpertUser expertUser);

    Optional<ExpertUser> findById(ExpertUser expertUser);
}
