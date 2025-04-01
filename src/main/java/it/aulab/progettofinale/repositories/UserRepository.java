package it.aulab.progettofinale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.aulab.progettofinale.models.User;

@Repository // Annotazione per indicare che questa interfaccia è un repository
// Spring la gestirà come un bean
public interface UserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
