package board.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class MyUserDetails implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	protected List<String> roles;
	
	public abstract int getNo();
	public abstract char getTarget();
	
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for(String role : this.roles) {
			roles.add(()-> {return "ROLE_"+role;}); 
		}
		return roles;
	}
}
