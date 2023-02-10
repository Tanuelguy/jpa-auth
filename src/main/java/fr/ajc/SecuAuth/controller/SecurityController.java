package fr.ajc.SecuAuth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.ajc.SecuAuth.models.CustomRole;
import fr.ajc.SecuAuth.models.CustomUser;
import fr.ajc.SecuAuth.services.RoleServiceInterface;
import fr.ajc.SecuAuth.services.UserServiceInterface;

@Controller
@RequestMapping("/")
public class SecurityController {
	private UserServiceInterface userServiceInterface;
	private RoleServiceInterface roleServiceInterface;
	private PasswordEncoder passwordEncoder;

	public SecurityController(UserServiceInterface userServiceInterface, RoleServiceInterface roleServiceInterface,
			PasswordEncoder passwordEncoder) {
		this.userServiceInterface = userServiceInterface;
		this.roleServiceInterface = roleServiceInterface;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/users")
	public ModelAndView returnUsersHTML(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("view_users");
		List<CustomUser> users=userServiceInterface.findAllUser();
		mav.addObject("users",users);
		return mav;
	}
	@GetMapping("/me")
	public ModelAndView returnMeHTML(Authentication authentication) {
		ModelAndView mav = new ModelAndView("view_users");
		List<CustomUser> users=new ArrayList<CustomUser>();
		users.add(userServiceInterface.getByUserName(authentication.getName()));
		mav.addObject("users",users);
		return mav;
	}
	  @PostMapping("/register")
	    public CustomUser registerUser(@RequestBody CustomUser user) throws RoleNotFoundException {
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        CustomRole roleUser = roleServiceInterface.getByRoleName("ROLE_USER");
	        user.setRoles(List.of(roleUser));
	        return userServiceInterface.addUser(user);
	    }
}
