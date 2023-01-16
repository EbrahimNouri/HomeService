package ir.maktab.homeservice.service.expertUser;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionOrderType;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import ir.maktab.homeservice.service.expert.ExpertService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ExpertUserServiceImpl implements ExpertUserService {
    private ExpertUserRepository repository;
    private ExpertService expertService;


    @Transactional
    @Override
    public void addCommentAndPoint(ExpertUser expertUser) {
        Expert expert = expertUser.getExpert();

        if (!expertUser.getOrder().getOrderType().equals(OrderType.PAID))
            throw new CustomExceptionOrderType("order type is invalid");


        if (expertUser.getPoint() >= 5.0 && expertUser.getPoint() <= 0.0)
            throw new CustomExceptionSave("point is invalid");

        Double average = repository.getAveragePoint(expert.getId());
        expertService.SetAveragePoint(average, expert.getId());

        repository.save(expertUser);


    }

    @Override
    public ExpertUser findByExpertUserOrder(ExpertUser expertUser) {
        return repository.findByExpertIdAndUserIdAndOrderId(
                expertUser.getExpert().getId()
                , expertUser.getUser().getId()
                , expertUser.getOrder().getId()
        ).orElseThrow(() -> new CustomExceptionNotFind("comment not found"));
    }

    @Override
    public ExpertUser findByOrderId(Long orderId, Long userId) {
        List<ExpertUser> byOrderIdNative = repository.findByOrderIdNative(orderId);
        if (!byOrderIdNative.isEmpty())
            return byOrderIdNative.get(0);

        throw new CustomExceptionNotFind("not found comment point");

    }

    @Override
    public void deductPoints(int hours, Order order) {

        Expert expert = order.getExpertUser().getExpert();
        User user = order.getExpertUser().getUser();

        addCommentAndPoint(new ExpertUser(expert, user, order
                , LocalDate.now(), (double) -hours, null));

        Double countAll = repository.countOfAllPointByExpertId(expert.getId()) - hours;


        if (countAll < 0)
            expertService.deactivate(expert);

        else
            expertService.save(expert);

    }

    @Override
    public List<Double> listOfScore(Long id) {
        return repository.listOfScore(id);
    }

    @Override
    public List<ExpertUser> findByUser(Long user){
        return repository.findExpertUserByUserId(user);
    }
}