package ir.maktab.homeservice.service.typeService;


import ir.maktab.homeservice.entity.TypeService;

import java.util.List;
import java.util.Optional;

public interface TypeServiceService {
    Optional<TypeService> findById(Long id);

    void addSubService(TypeService typeService);

    void paymentPriceChange(TypeService typeService, double price);

    List<TypeService> showTypeServices(Long basicServiceId);
}
