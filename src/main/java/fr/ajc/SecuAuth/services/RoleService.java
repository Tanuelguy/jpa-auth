package fr.ajc.SecuAuth.services;

import javax.management.relation.RoleNotFoundException;

import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;

import fr.ajc.SecuAuth.repositories.RoleRepository;

@Service
public class RoleService {

	private RoleRepository roleRepository; 
	
	  public RoleService(RoleRepository roleRepository) {
	        this.roleRepository = roleRepository;
	    }
	  
	  public Role getByRoleName(String roleName) throws RoleNotFoundException{
		  return roleRepository.findbyRoleName(roleName).orElseThrow(()-> 
		  new RoleNotFoundException(String.format("Role %s was not found !",roleName)));
	  }
}
 