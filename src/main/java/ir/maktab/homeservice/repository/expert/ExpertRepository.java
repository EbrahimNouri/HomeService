package ir.maktab.homeservice.repository.expert;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    @Query("update Expert e set e.expertStatus = :expertStatus where e.id = :id")
    void deactivate(Long id, ExpertStatus expertStatus);
}
