package ir.maktab.homeservice.repository.expertUser;

import ir.maktab.homeservice.entity.ExpertUser;
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

    Optional<ExpertUser> findByExpertIdAndUserIdAndOrderId(Long expert, Long user, Long order);

    @Query(value = "select * from comment_point where order_id = :orderId", nativeQuery = true)
    Optional<ExpertUser> findByOrderIdNative(Long orderId);

    @Query("select e from ExpertUser e where e.order.id = :orderId")
    Optional<ExpertUser> findByOrderIdQ(Long orderId);
}
