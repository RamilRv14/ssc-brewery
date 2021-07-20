package guru.sfg.brewery.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    //* Converted our User class into Spring User class cuz we gave default values into User, normally it has to be MyUserClass extends User(Spring) *//
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("User by name: " + s + " is not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(), user.getAccountNonLocked(),
                convertToGrantedAuthority(user.getAuthorities()));
    }

    //* Converted our Authority class into SimpleGrantedAuthority *//
    private Collection<? extends GrantedAuthority> convertToGrantedAuthority(Set<Authority> authorities) {
        if (authorities != null && authorities.size() > 0) {
            return authorities.stream().map(Authority::getRole).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return null;
    }

}
