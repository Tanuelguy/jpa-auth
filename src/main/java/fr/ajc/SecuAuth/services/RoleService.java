package fr.ajc.SecuAuth.services;

import javax.management.relation.RoleNotFoundException;

import org.springframework.stereotype.Service;

import fr.ajc.SecuAuth.models.CustomRole;
import fr.ajc.SecuAuth.repositories.RoleRepository;

@Service
public class RoleService implements RoleServiceInterface{

	private RoleRepository roleRepository; 
	
	  public RoleService(RoleRepository roleRepository) {
	        this.roleRepository = roleRepository;
	    }
	  
	  public CustomRole getByRoleName(String roleName) throws RoleNotFoundException{
		  return roleRepository.findbyRoleName(roleName).orElseThrow(()-> 
		  new RoleNotFoundException(String.format("Role %s was not found !",roleName)));
	  }
}
 