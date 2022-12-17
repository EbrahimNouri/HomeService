package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Order;

import java.util.Optional;

public interface ExpertUserService {

    void addCommentAndPoint(ExpertUser expertUser);

    Optional<ExpertUser> findById(ExpertUser expertUser);

    void deductPoints(int hours, Order order);
}
