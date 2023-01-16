package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Order;

import java.util.List;

public interface ExpertUserService {

    void addCommentAndPoint(ExpertUser expertUser);

    ExpertUser findByExpertUserOrder(ExpertUser expertUser);

    ExpertUser findByOrderId(Long orderId, Long userId);

    void deductPoints(int hours, Order order);

    List<Double> listOfScore(Long id);

    List<ExpertUser> findByUser(Long user);
}
