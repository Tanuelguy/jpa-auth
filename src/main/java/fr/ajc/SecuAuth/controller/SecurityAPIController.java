package fr.ajc.SecuAuth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ajc.SecuAuth.models.CustomRole;
import fr.ajc.SecuAuth.models.CustomUser;
import fr.ajc.SecuAuth.services.RoleServiceInterface;
import fr.ajc.SecuAuth.services.UserServiceInterface;

@RestController
@RequestMapping("/api")
public class SecurityAPIController {

	private PasswordEncoder passwordEncoder;
	private UserServiceInterface userServiceInterface;

	public SecurityAPIController(PasswordEncoder passwordEncoder, UserServiceInterface userServiceInterface) {
    	this.passwordEncoder = passwordEncoder;
        this.userServiceInterface = userServiceInterface;

    }

	@GetMapping("/users")
	public List<CustomUser> returnUsers(@RequestParam Long id) {
		List<CustomUser> lUser= new ArrayList<CustomUser>();
		if(id!=null) {
			lUser.add(userServiceInterface.findByUserId(id));
			return lUser;
		}else
			return userServiceInterface.findAllUser();
	}

	@GetMapping(value = "/me")
    @ResponseBody
	public CustomUser returnMe(Authentication authentication) {
		return userServiceInterface.getByUserName(authentication.getName());
	}

	@GetMapping("/add-user")
	public CustomUser addUser(@RequestBody CustomUser user, @RequestBody List<CustomRole> role) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(role);
		return userServiceInterface.addUser(user);
	}
	
	@GetMapping("/change-role")
	public CustomUser changeRole(@RequestParam Long id,@RequestParam CustomRole role) {
		CustomUser user= userServiceInterface.findByUserId(id);
		List<CustomRole> rolesUser=user.getRoles();
		rolesUser.add(role);
		user.setRoles(rolesUser);
		return userServiceInterface.update(id, user);
	}
}
