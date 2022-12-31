package ir.maktab.homeservice.repository.expert;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Component
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
    @Modifying
    @Query("update Expert e set e.expertStatus = :expertStatus where e.id = :id")
    void deactivate(Long id, ExpertStatus expertStatus);

    @Modifying
    @Query("update Expert e set e.averageScore = :point where e.id = :expertId")
    void setAveragePont(Double point, Long expertId);

    List<Expert> findByFirstname(String firstname);

    List<Expert> findByLastname(String lastname);

    Optional<Expert> findByEmail(String email);

    @Query("from Expert e where e.id =:id")
    Optional<Expert> findByIdCustom( Long id);

    Optional<Expert> findByUsername(String username);

}
