package app.security;


import app.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



public class UserDetailsImpl implements UserDetailsService {
    private UserRepository userRepository;
    public UserDetailsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findUserByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User"+ email +"not found"));
    }
}
