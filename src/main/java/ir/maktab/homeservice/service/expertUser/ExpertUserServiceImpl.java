package ir.maktab.homeservice.service.expertUser;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class ExpertUserServiceImpl implements ExpertUserService {
    private ExpertUserRepository repository;
    private ExpertRepository expertRepository;


    @Transactional
    @Override
    public void addCommentAndPoint(ExpertUser expertUser) {
        try {
            Expert expert = expertUser.getExpert();
            if (expertUser.getOrder().getOrderType().equals(OrderType.DONE)
                    && expertUser.getPoint() <= 5.0
                    && expertUser.getComment() != null) {

                repository.save(expertUser);
                expert.setAverageScore(repository.getAveragePoint(expert.getId()));
                expertRepository.save(expert);

            }
        } catch (Exception e) {
            log.error("error add comment point {} ", expertUser, e);
//            throw new CustomExceptionSave("expert user not worked");
            throw e;
        }

    }

    @Override
    public Optional<ExpertUser> findById(ExpertUser expertUser) {
        Optional<ExpertUser> expertUser1 = Optional.empty();
        try {
            expertUser1 = repository.findByExpertAndUser(expertUser.getExpert(), expertUser.getUser());
        } catch (Exception e) {
            /* TODO: 12/11/2022 AD */
        }
        return expertUser1;
    }

    @Override
    public void deductPoints(int hours, Order order) {

        Expert expert = new Expert();
        User user = new User();

        addCommentAndPoint(new ExpertUser(expert, user, order
                , LocalDate.now(), (double) hours, null));

        Double averagePoint = repository.countOfAllPointByExpertId(expert.getId());

        if (averagePoint < 0) {
            expertRepository.deactivate(expert.getId(), ExpertStatus.DEACTIVATE);

        } else
            expertRepository.save(expert);
    }
}