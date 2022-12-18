package ir.maktab.homeservice.service.typeService;


import ir.maktab.homeservice.entity.TypeService;
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
    public Optional<TypeService> findById(Long id){
        return typeServiceRepository.findById(id);
    }

    @Override
    public void addSubService(TypeService typeService) {
        try {
            typeServiceRepository.save(typeService);

            log.debug("debug add sub service {} ", typeService);
        } catch (Exception e) {
            log.error("error add sub service {} ", typeService, e);
        }
    }

    @Override
    public void paymentPriceChange(TypeService typeService, double price) {
        try {

            typeService.setBasePrice(price);
            typeServiceRepository.save(typeService);

            log.debug("debug payment price change {} to {} ", typeService, price);
        } catch (Exception e) {

            log.error("error payment price change {} to {} ", typeService, price, e);

        }
    }

    @Override
    public List<TypeService> showTypeServices(Long basicServiceId) {
        List<TypeService> showAll = new ArrayList<>();
        try {
            showAll = typeServiceRepository.findByBasicServiceId(basicServiceId);
            log.debug("debug show type services for basic id {} ", basicServiceId);
        } catch (Exception e) {
            log.error("error show type services", e);
        }
        return showAll;
    }
}
