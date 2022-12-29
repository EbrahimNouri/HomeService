package ir.maktab.homeservice.service.typeService;


import ir.maktab.homeservice.entity.TypeService;

import java.util.List;

public interface TypeServiceService {
    TypeService findById(Long id);

    void addSubService(TypeService typeService);

    void paymentPriceChange(TypeService typeService, double price);

    List<TypeService> showTypeServices(Long basicServiceId);

    void descriptionChange(Long typeServiceId, String description);

    List<TypeService> findByBasicServiceId(Long basicId);

    void save(TypeService typeService);
}
