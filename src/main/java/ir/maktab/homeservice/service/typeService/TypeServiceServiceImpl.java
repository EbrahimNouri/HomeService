package ir.maktab.homeservice.service.typeService;


import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.exception.CustomExceptionInvalid;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class TypeServiceServiceImpl implements TypeServiceService {

    private final TypeServiceRepository typeServiceRepository;
    private final BasicServicesService basicServicesService;

    @Override
    public TypeService findById(Long id) {
        return typeServiceRepository.findById(id)
                .orElseThrow(() -> new CustomExceptionNotFind("type service not found"));
    }

    @Override
    public void addSubService(TypeService typeService) {

        if (typeServiceRepository.existsBySubService(typeService.getSubService()))
            throw new CustomExceptionInvalid("this type service is invalid");

        if (!basicServicesService.existByName(typeService.getBasicService().getName()))
            throw new CustomExceptionInvalid("this basic service is invalid");

        typeServiceRepository.save(typeService);
    }

    @Override
    public void paymentPriceChange(TypeService typeService, double price) {

        typeService.setBasePrice(price);
        typeServiceRepository.save(typeService);

        log.debug("debug payment price change {} to {} ", typeService, price);

    }

    @Override
    public List<TypeService> showTypeServices(Long basicServiceId) {
        log.debug("debug show type services for basic id {} ", basicServiceId);

        return typeServiceRepository.findByBasicServiceId(basicServiceId);

    }

    @Override
    public void descriptionChange(Long typeServiceId, String description) {

        TypeService typeService = findById(typeServiceId);
        typeService.setDescription(description);
        typeServiceRepository.save(typeService);

    }

    @Override
    public List<TypeService> findByBasicServiceId(Long basicId) {
        return typeServiceRepository.findByBasicServiceId(basicId);
    }

    @Override
    public void save(TypeService typeService) {
        typeServiceRepository.save(typeService);
    }
}
