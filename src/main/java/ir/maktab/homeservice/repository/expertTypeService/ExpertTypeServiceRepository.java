package ir.maktab.homeservice.repository.expertTypeService;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpertTypeServiceRepository extends JpaRepository<ExpertTypeService, ExpertTypeServiceId> {

//    void addTypeServiceToExpert(ExpertTypeService expertTypeService);

    List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId);

    void removeByExpert(Expert expert);

    Optional<ExpertTypeService> findByExpertIdAndTypeServiceId(Long expert, Long typeService);

}