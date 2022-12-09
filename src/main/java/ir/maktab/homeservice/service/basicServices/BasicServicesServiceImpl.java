package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@AllArgsConstructor
public class BasicServicesServiceImpl implements BasicServicesService {

    private final BasicServiceRepository repository;

    @Override
    public void addBasicService(BasicService basicService) {
        if (!repository.findByName(basicService.getName())) {
            repository.save(basicService);
        }
    }


    @Override
    public void removeBasicService(BasicService basicService) {

        if (repository.findByName(basicService.getName()))
            repository.delete(basicService);

    }

    @Override
    public List<BasicService> showAllBasicService() {

        return repository.findAll();

    }
}
