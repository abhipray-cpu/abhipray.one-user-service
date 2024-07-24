package one.abhipray.User_Service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import one.abhipray.User_Service.model.User;
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Placeholder for actual authorities. Should be replaced with real authorities based on the application's requirements.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Assuming email is used as the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Placeholder implementation
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Placeholder implementation
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Placeholder implementation
    }

    @Override
    public boolean isEnabled() {
        return true; // Placeholder implementation
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Long getId() {
        return user.getId();
    }
}