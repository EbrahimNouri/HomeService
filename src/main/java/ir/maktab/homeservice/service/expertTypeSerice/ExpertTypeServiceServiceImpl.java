package ir.maktab.homeservice.service.expertTypeSerice;


import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.util.ApplicationContextProvider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class ExpertTypeServiceServiceImpl implements ExpertTypeServiceService {
    private final TypeServiceRepository typeServiceRepository;
    private final ExpertTypeServiceRepository repository;
    private final ApplicationContextProvider applicationContext;

    @Override
    @Transactional
    public void removeExpertFromBasicService(Long expert) {

        repository.removeExpertTypeServiceByExpertId(expert);

    }

    @Override
    public void addExpertToTypeService(ExpertTypeService expertTypeService) {
        List<ExpertTypeService> expertTypeServices = findExpertTypeServiceByExpertId(expertTypeService.getExpert().getId());

        if (repository.findById
                (expertTypeService.getExpert().getId(), expertTypeService.getTypeService().getId()).isEmpty()
                || repository.findBasicService(expertTypeService.getTypeService().getId())
                .orElseThrow(() -> new CustomExceptionNotFind("expert type service not found"))
                .equals(expertTypeServices.get(0).getTypeService().getBasicService())) {

            repository.save(expertTypeService);
        }
    }

    @Override
    public void removeExpertFromTypeService(ExpertTypeService expertTypeService) {
        Optional<ExpertTypeService> temp = repository
                .findById(expertTypeService.getExpert().getId()
                        , expertTypeService.getTypeService().getId());

        temp.ifPresent(typeService -> repository.delete(expertTypeService));

    }

    @Override
    public ExpertTypeService findById(Long expertId, Long typeServiceId) {

        return repository.findById(expertId, typeServiceId)
                .orElseThrow(() -> new CustomExceptionNotFind("expert type service not found"));

    }

    @Override
    public List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId) {
        return repository.findExpertTypeServiceByExpertId(expertId);

    }


    @Override
    public List<Order> findByExpertId(Long expertId) {
        List<TypeService> typeServices = repository.findByExpertId(expertId);
        if (typeServices.isEmpty())
            throw new CustomExceptionNotFind("type service is empty");

        OrderService bean = applicationContext.getContext()
                .getBean(OrderService.class);

        List<Order> ordersByTypeServices = bean.findOrdersByTypeServices(typeServices);
        if (ordersByTypeServices.isEmpty())
            throw new CustomExceptionNotFind("any order not found");

        return ordersByTypeServices;

    }
}
