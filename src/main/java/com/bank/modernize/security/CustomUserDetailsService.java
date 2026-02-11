package com.bank.modernize.security;

<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
>>>>>>> origin/main
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.bank.modernize.repository.UserRepository;

@Service
<<<<<<< HEAD
=======
@RequiredArgsConstructor
>>>>>>> origin/main
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

<<<<<<< HEAD
    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

=======
>>>>>>> origin/main
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
