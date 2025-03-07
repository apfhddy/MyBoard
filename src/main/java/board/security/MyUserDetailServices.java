package board.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import board.Board_Service.admin_Service;
import board.Board_Service.role_Service;
import board.Board_Service.users_Service;

@Configuration
public class MyUserDetailServices implements UserDetailsService{
	
	private users_Service users_Service;
	private admin_Service admin_Service;
	private role_Service role_Service;
	
	
	public MyUserDetailServices(
			users_Service users_Service,
			admin_Service admin_Service,
			role_Service role_Service
	) 
	{
		this.users_Service = users_Service;
		this.admin_Service = admin_Service;
		this.role_Service = role_Service;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserDetails sendObject = null;
		
		sendObject = users_Service.getUserById(username);
		if(sendObject == null) sendObject = admin_Service.getAdminById(username); 
		
		if(sendObject == null)throw new UsernameNotFoundException("존재하지 않는 아이디입니다");
		
		sendObject.setRoles(role_Service.getRoles(sendObject.getNo(), sendObject.getTarget()));
		
		return sendObject;
	
	}

}
