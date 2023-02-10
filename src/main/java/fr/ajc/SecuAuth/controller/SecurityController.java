package fr.ajc.SecuAuth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.ajc.SecuAuth.models.CustomRole;
import fr.ajc.SecuAuth.models.CustomUser;
import fr.ajc.SecuAuth.services.RoleServiceInterface;
import fr.ajc.SecuAuth.services.UserServiceInterface;
import jakarta.servlet.http.HttpServletResponse;

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

	   @Value("${spring.h2.console.path}")
	    private String h2ConsolePath;

	    @GetMapping({"", "/"})
	    public void redirectToHome(HttpServletResponse response) throws IOException {
	        response.sendRedirect("/home");
	    }

	    @GetMapping("/home")
	    public Map<String, String> homePage() {
	        Map<String, String> routes = new HashMap<>();
	        routes.put("users", "/users");
	        routes.put("me", "/me");
	        routes.put("register", "/register");
//	        routes.put("apiuser", "/api/users");
//	        routes.put("apime", "/api/me");
//	        routes.put("apiadd-user","/api/add-user");
//	        routes.put("apichange-role","/api/change-role");
	        routes.put("h2", h2ConsolePath);
	        return routes;
	    }
	@GetMapping("/users")
	public ModelAndView returnUsersHTML(@RequestParam(required = false) Long id) {
		ModelAndView mav = new ModelAndView("view_users");
		List<CustomUser> lUser= new ArrayList<CustomUser>();
		if(id!=null) {
			lUser.add(userServiceInterface.findByUserId(id));
		}else {
			lUser=userServiceInterface.findAllUser();
		}
		mav.addObject("users",lUser);
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
