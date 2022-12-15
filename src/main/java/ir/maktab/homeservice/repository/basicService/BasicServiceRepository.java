package ir.maktab.homeservice.repository.basicService;

import ir.maktab.homeservice.entity.BasicService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BasicServiceRepository extends JpaRepository<BasicService, Long> {

    boolean existsByName(String name);

    Optional<BasicService> findBasicServiceById(Long id);
    @Query("from BasicService ")
    List<BasicService> findAllC();
}
