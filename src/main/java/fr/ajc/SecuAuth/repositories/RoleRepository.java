package fr.ajc.SecuAuth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.ajc.SecuAuth.models.CustomRole;


public interface RoleRepository extends JpaRepository<CustomRole, Long> {
    @Query("select r from CustomRole r where r.roleName = :roleName")
    Optional<CustomRole> findbyRoleName(@Param("roleName") String roleName);
}
