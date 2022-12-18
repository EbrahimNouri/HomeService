package ir.maktab.homeservice.repository.expertUser;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.id.ExpertUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertUserRepository extends JpaRepository<ExpertUser, ExpertUserId> {
    @Query("select avg(exu.point) from ExpertUser exu where exu.expert.id = :expertId")
    Double getAveragePoint(@Param(value = "expertId") Long expertId);

    @Query("select count(eu.point) from ExpertUser eu where eu.expert.id = :ExpertId")
    Double countOfAllPointByExpertId(Long ExpertId);

    Optional<ExpertUser> findByExpertAndUser(Expert expert, User user);

    Optional<ExpertUser> findExpertUserByOrderId(Long orderId);
}
