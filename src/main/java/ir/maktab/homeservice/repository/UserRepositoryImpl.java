package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl {
    @PersistenceContext
    EntityManager entityManager;

    public void saveeed(User user){
            entityManager.persist(user);
    }
}
