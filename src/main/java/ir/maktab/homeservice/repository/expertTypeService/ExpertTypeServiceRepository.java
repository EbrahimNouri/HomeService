package ir.maktab.homeservice.repository.expertTypeService;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpertTypeServiceRepository extends JpaRepository<ExpertTypeService, ExpertTypeServiceId> {

//    void addTypeServiceToExpert(ExpertTypeService expertTypeService);

    List<ExpertTypeService> findExpertTypeServiceByExpertId(Long expertId);

    @Modifying
    @Query("delete from ExpertTypeService et where et.expert = :expert")
    int removeByExpert(Expert expert);

    @Query(value = "select * from expert_type_service et where expert_id = :expert and type_service_id = :typeService", nativeQuery = true)
    Optional<ExpertTypeService> findById(Long expert, Long typeService);

    @Query("select et.typeService.basicService from ExpertTypeService et where et.typeService = :typeService ")
    Optional<BasicService> findBasicService(Long typeService);
}
