package ir.maktab.homeservice.repository.expert;


import ir.maktab.homeservice.entity.base.Person;
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
public interface ExpertRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    @Modifying
    @Query("update Person e set e.expertStatus = :expertStatus where e.id = :id")
    void deactivate(Long id, ExpertStatus expertStatus);

    @Modifying
    @Query("update Person e set e.averageScore = :point where e.id = :expertId")
    void setAveragePont(Double point, Long expertId);

    List<Person> findByFirstname(String firstname);

    List<Person> findByLastname(String lastname);

    Optional<Person> findByEmail(String email);

    @Query("from Person e where e.id =:id")
    Optional<Person> findByIdCustom( Long id);

    Optional<Person> findByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Person> findByVerificationCode(Integer code);

    List<Person> findByExpertStatus(ExpertStatus expertStatus);
}
