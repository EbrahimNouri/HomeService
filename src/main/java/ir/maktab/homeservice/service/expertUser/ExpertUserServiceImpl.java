package ir.maktab.homeservice.service.expertUser;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ExpertUserServiceImpl implements ExpertUserService {
    private ExpertUserRepository repository;
    private ExpertRepository expertRepository;

    @Transactional
    @Override
    public void addCommentAndPoint(ExpertUser expertUser) {
        Expert expert = expertUser.getExpert();
        User user = expertUser.getUser();
        if (expertUser.getOrder().getOrderType().equals(OrderType.DONE)
                && expertUser.getPoint() >= 5.0 && expertUser.getPoint() <= 0.0
                && expertUser.getComment() != null) {

            repository.save(expertUser);
            expert.setAverageScore(repository.getAveragePoint(expert.getId()));
            expertRepository.save(expert);

        }
    }
}