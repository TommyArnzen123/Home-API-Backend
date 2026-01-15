package com.arnzen.home_api_backend.service;


import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.model.base.UserEntity;
import com.arnzen.home_api_backend.model.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = repo.findByUsernameIgnoreCase(username);
	
		if (user == null) {
			throw new UsernameNotFoundException("User 404");
		}

		return new UserPrincipal(user);
	}
}
