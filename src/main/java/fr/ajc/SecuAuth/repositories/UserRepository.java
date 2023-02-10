package fr.ajc.SecuAuth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ajc.SecuAuth.models.CustomUser;

public interface UserRepository extends JpaRepository<CustomUser, Long>{
    Optional<CustomUser> findByUsername(String username);

}
