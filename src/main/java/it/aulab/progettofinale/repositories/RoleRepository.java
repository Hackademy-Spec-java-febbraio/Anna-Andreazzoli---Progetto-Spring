package it.aulab.progettofinale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.aulab.progettofinale.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name); //ricerca tramite nome del ruolo
    
}
