package ir.maktab.homeservice.repository.expert;


import ir.maktab.homeservice.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

}
