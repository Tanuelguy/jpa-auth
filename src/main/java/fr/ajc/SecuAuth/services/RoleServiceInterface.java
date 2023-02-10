package fr.ajc.SecuAuth.services;

import org.springframework.context.annotation.Role;

public interface  RoleServiceInterface {
	Role getByRoleName(String roleName);
}
