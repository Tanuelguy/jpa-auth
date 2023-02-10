package fr.ajc.SecuAuth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.ajc.SecuAuth.models.CustomRole;
import fr.ajc.SecuAuth.models.CustomUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private UserService userService;

	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}
    private List<GrantedAuthority> rolesToAuthority(List<CustomRole> roles) {
        return roles.stream().map(CustomRole::getRoleName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    }
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 CustomUser user = userService.getByUserName(username);
	        return new User(user.getUsername(), user.getPassword(), rolesToAuthority(user.getRoles()));
	    }


}
