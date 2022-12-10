package ir.maktab.homeservice.repository.typeService;


import ir.maktab.homeservice.entity.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeServiceRepository extends JpaRepository<TypeService, Long> {
    @Query("from TypeService ts where ts.basicService.id = :basicServiceId")
    List<TypeService> findByBasicServiceId(@Param("basicServiceId") Long basicServiceId);
}
