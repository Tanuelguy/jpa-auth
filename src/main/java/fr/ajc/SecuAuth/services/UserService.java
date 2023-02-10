package fr.ajc.SecuAuth.services;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.ajc.SecuAuth.models.CustomUser;
import fr.ajc.SecuAuth.repositories.UserRepository;

@Service
public class UserService implements UserServiceInterface {
	UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public CustomUser findByUserId(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %d not found!", id)));
	}

	@Override
	public List<CustomUser> findAllUser() {
		return userRepository.findAll();
	}

	@Override
	public CustomUser getByUserName(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found!", username)));
	}

	@Override
	public CustomUser addUser(CustomUser user) {
		return userRepository.save(user);
	}

	@Override
	public CustomUser update(Long id, CustomUser user) {
		user.setId(id);
		return userRepository.save(user);
	}

}
