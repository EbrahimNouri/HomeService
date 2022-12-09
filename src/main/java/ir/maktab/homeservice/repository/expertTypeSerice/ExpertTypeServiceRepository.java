package ir.maktab.homeservice.repository.expertTypeSerice;

import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ExpertTypeServiceRepository extends JpaRepository<ExpertTypeService, ExpertTypeServiceId> {

    @Modifying
    @Query("delete from ExpertTypeService where expert.id =:expertId")
    void removeTypeServiceByExpertId( @Param(value = "expertId")Long expertId);

    @Query("from ExpertTypeService ex where ex.expertTypeServiceId.expert.id = :expId and ex.expertTypeServiceId.typeService.id = :typId")
    Optional<ExpertTypeService> findByExpertAndTypeService(Long expId, Long typId);

}

