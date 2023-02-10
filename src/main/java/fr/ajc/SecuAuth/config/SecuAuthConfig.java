package fr.ajc.SecuAuth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.ajc.SecuAuth.models.CustomRole;
import fr.ajc.SecuAuth.models.CustomUser;
import fr.ajc.SecuAuth.repositories.RoleRepository;
import fr.ajc.SecuAuth.repositories.UserRepository;
import fr.ajc.SecuAuth.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecuAuthConfig {

	CustomUserDetailsService userDetailsService;

	@Value("${spring.h2.console.path}")
	private String h2ConsolePath = "h2-ui";

	public SecuAuthConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> request.requestMatchers("/home/**", "/register/**", "", "/")
						.permitAll()
						// .requestMatchers(h2ConsolePath + "/**").authenticated()
						.requestMatchers("/users/**", "/add-user/**", "change-role/**").hasRole("ADMIN")
						.requestMatchers("/api/users/**", "/api/add-user/**", "/api/change-role/**").hasRole("ADMIN")
						.requestMatchers("/me/**", "/api/me/**").hasRole("USER").anyRequest().authenticated())
				.formLogin((form) -> form.loginPage("/login").permitAll()).logout(logout -> logout.permitAll())
				.userDetailsService(userDetailsService).headers(headers -> headers.frameOptions().sameOrigin());
		return http.build();
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {// , RoleRepository roleRepository) {
		return args -> {
			CustomRole roleAdmin = new CustomRole("ROLE_ADMIN");
			CustomRole roleUser = new CustomRole("ROLE_USER");
			CustomUser user = new CustomUser("user", passwordEncoder().encode("pass"), List.of(roleAdmin, roleUser));
			List<CustomRole> roles = new ArrayList<CustomRole>();
			roles.add(roleUser);
			roles.add(roleAdmin);
			// roleRepository.saveAll(roles);
			userRepository.save(user);
		};
	}
}
