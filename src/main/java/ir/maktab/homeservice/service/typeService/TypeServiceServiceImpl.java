package ir.maktab.homeservice.service.typeService;


import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class TypeServiceServiceImpl implements TypeServiceService {

    private TypeServiceRepository typeServiceRepository;

    @Override
    public TypeService findById(Long id) {
        return typeServiceRepository.findById(id).orElseThrow(() -> new CustomExceptionNotFind("type service not found"));
    }

    @Override
    public void addSubService(TypeService typeService) {
        try {
            typeServiceRepository.save(typeService);

            log.debug("debug add sub service {} ", typeService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paymentPriceChange(TypeService typeService, double price) {
        try {
            typeService.setBasePrice(price);
            typeServiceRepository.save(typeService);

            log.debug("debug payment price change {} to {} ", typeService, price);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TypeService> showTypeServices(Long basicServiceId) {
        try {
            log.debug("debug show type services for basic id {} ", basicServiceId);

            return typeServiceRepository.findByBasicServiceId(basicServiceId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void descriptionChange(TypeService typeService, String description) {
        try {

            typeService.setDescription(description);
            typeServiceRepository.save(typeService);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
