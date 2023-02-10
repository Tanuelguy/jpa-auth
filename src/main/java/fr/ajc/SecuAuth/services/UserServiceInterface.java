package fr.ajc.SecuAuth.services;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.ajc.SecuAuth.models.CustomUser;
@Service
public interface UserServiceInterface {
	CustomUser findByUserId(Long id);
	List<CustomUser> findAllUser();
	CustomUser getByUserName(String username);
    CustomUser addUser(CustomUser user);
	CustomUser update(Long id, CustomUser user);

}
