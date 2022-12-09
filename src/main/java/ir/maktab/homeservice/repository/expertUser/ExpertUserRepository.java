package ir.maktab.homeservice.repository.expertUser;

import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.id.ExpertUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertUserRepository extends JpaRepository<ExpertUser, ExpertUserId> {
    @Query("select avg(exu.point) from ExpertUser exu where exu.expertUserId.expert.id = :expertId")
    Double getAveragePoint(@Param(value = "expertId") Long expertId);

}
