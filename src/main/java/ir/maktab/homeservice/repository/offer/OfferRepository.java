package ir.maktab.homeservice.repository.offer;


import ir.maktab.homeservice.entity.Offer;

import ir.maktab.homeservice.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findOfferByOrder_Id(Long order);

    Optional<Offer> findOfferById(Long id);

}
