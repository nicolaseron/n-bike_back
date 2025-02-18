package com.example.n_bike.services;

import com.example.n_bike.command.RegisterCommand;
import com.example.n_bike.entity.Role;
import com.example.n_bike.entity.User;
import com.example.n_bike.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new UserDetailsImpl(user);
  }

  public User createUser(RegisterCommand command) {
    User user = new User();
    user.setFirstName(command.getFirstName());
    user.setLastName(command.getLastName());
    user.setEmail(command.getEmail());
    user.setUsername(command.getUsername());
    user.setPassword(passwordEncoder.encode(command.getPassword()));
    user.setRole(Role.USER);
    return userRepository.save(user);
  }
}
