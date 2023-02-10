package fr.ajc.SecuAuth.services;


import fr.ajc.SecuAuth.models.CustomRole;

public interface  RoleServiceInterface {
	CustomRole getByRoleName(String roleName);
}
