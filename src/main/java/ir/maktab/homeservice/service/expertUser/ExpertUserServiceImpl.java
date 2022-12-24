package ir.maktab.homeservice.service.expertUser;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import ir.maktab.homeservice.service.expert.ExpertService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class ExpertUserServiceImpl implements ExpertUserService {
    private ExpertUserRepository repository;
    private ExpertService expertService;


    @Transactional
    @Override
    public void addCommentAndPoint(ExpertUser expertUser) {
        try {
            Expert expert = expertUser.getExpert();
            if (expertUser.getOrder().getOrderType().equals(OrderType.PAID)
                    && expertUser.getPoint() <= 5.0 && expertUser.getPoint() >= 0.0) {

                Double average = repository.getAveragePoint(expert.getId());
                expertService.SetAveragePoint(average, expert.getId());

                repository.save(expertUser);
            } else {
                log.warn("error add comment point {} ", expertUser);
                throw new CustomExceptionSave("expert user not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ExpertUser> findByExpertUserOrder(ExpertUser expertUser) {
        try {
            return repository.findByExpertIdAndUserIdAndOrderId(
                    expertUser.getExpert().getId()
                    , expertUser.getUser().getId()
                    , expertUser.getOrder().getId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<ExpertUser> findByOrderId(Long orderId) {
        try {
            return repository.findByOrderIdNative(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deductPoints(int hours, Order order) {

        Expert expert = order.getExpertUser().getExpert();
        User user = order.getExpertUser().getUser();
        try {
            addCommentAndPoint(new ExpertUser(expert, user, order
                    , LocalDate.now(), (double) -hours, null));

            Double averagePoint = repository.countOfAllPointByExpertId(expert.getId());

            if (averagePoint < 0) {
                expertService.deactivate(expert);

            } else
                expertService.save(expert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Double> listOfScore(Long id) {
        return repository.listOfScore(id);
    }
}