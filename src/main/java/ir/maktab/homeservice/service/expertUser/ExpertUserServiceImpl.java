package ir.maktab.homeservice.service.expertUser;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@AllArgsConstructor
public class ExpertUserServiceImpl implements ExpertUserService {
    private ExpertUserRepository repository;
    private ExpertRepository expertRepository;

    @Transactional
    @Override
    public void addCommentAndPoint(ExpertUser expertUser) {
        Expert expert = expertUser.getExpertUserId().getExpert();
        User user = expertUser.getExpertUserId().getUser();
        try {

            if (expertUser.getOrder().getOrderType().equals(OrderType.DONE)
                    && expertUser.getPoint() >= 5.0 && expertUser.getPoint() <= 0.0
                    && expertUser.getComment() != null) {

                repository.save(expertUser);
                expert.setAverageScore(repository.getAveragePoint(expert.getId()));
                expertRepository.save(expert);

                log.debug("debug add Comment and point {} ", expertUser);
            } else
                log.warn("warn order type isn't done {} ", expertUser.getOrder().getOrderType());

        }catch (Exception e){

            log.error("error order type isn't done {} ", expertUser.getOrder().getOrderType());
        }
    }
}