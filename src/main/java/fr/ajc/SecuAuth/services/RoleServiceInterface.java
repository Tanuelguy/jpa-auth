package fr.ajc.SecuAuth.services;


import javax.management.relation.RoleNotFoundException;

import org.springframework.stereotype.Service;

import fr.ajc.SecuAuth.models.CustomRole;
@Service
public interface  RoleServiceInterface {
	CustomRole getByRoleName(String roleName) throws RoleNotFoundException;
}
