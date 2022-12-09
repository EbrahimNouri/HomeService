package ir.maktab.homeservice.service.typeService;


import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TypeServiceServiceImpl implements TypeServiceService {

    private TypeServiceRepository typeServiceRepository;

    @Override
    public void addSubService(TypeService typeService) {
        try {
            typeServiceRepository.save(typeService);
        }catch (Exception e){
            // TODO: 12/9/2022 AD
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
