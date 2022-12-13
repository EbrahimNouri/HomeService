package ir.maktab.homeservice.service.expertUser;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpertUserServiceImpl implements ExpertUserService {
    private ExpertUserRepository repository;
    private ExpertRepository expertRepository;


    @Transactional
    @Override
    public void addCommentAndPoint(ExpertUser expertUser) {
        Expert expert = expertUser.getExpert();
        if (expertUser.getOrder().getOrderType().equals(OrderType.DONE)
                && expertUser.getPoint() <= 5.0 && expertUser.getPoint() >= 0.0
                && expertUser.getComment() != null) {

            repository.save(expertUser);
            expert.setAverageScore(repository.getAveragePoint(expert.getId()));
            expertRepository.save(expert);

        }
    }

    @Override
    public Optional<ExpertUser> findById(ExpertUser expertUser){
        Optional<ExpertUser> expertUser1 = Optional.empty();
        try {
            expertUser1 =  repository.findByExpertAndUser(expertUser.getExpert(), expertUser.getUser());
        }catch (Exception e){
            /* TODO: 12/11/2022 AD */
        }
        return expertUser1;
    }
}