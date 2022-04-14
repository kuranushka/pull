package ru.kuranov.pull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.entity.security.User;
import ru.kuranov.pull.repo.UserRepo;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username).orElseThrow();
        System.out.println();
        return user;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepo.findUserByUsername(username).orElseThrow();
//    }

//    public void saveUser(User user, String role, String active) {
//        Set<Role> roleSet = new HashSet<>();
//        if (role != null) {
//            String[] roles = role.split(",");
//            roleSet = Arrays.stream(roles)
//                    .map(Role::valueOf).collect(Collectors.toSet());
//            user.setRoles(roleSet);
//        }
//        boolean isActive = false;
//        if (active != null) {
//            isActive = true;
//        }
//
//        User newUser = User.builder()
//                .username(user.getUsername())
//                .mail(user.getMail())
//                .password(passwordEncoder.encode(user.getPassword()))
//                .roles(roleSet)
//                .active(isActive)
//                .build();
//
//        if (user.getId() != null) {
//            newUser.setId(user.getId());
//            newUser.setActivationCode(user.getActivationCode());
//        }
//        userRepo.save(newUser);
//    }

//    public Optional<User> findByActivationCode(String code) {
//        return userRepo.findByActivationCode(code);
//    }

//    public boolean activateUser(String code) {
//        Optional<User> user = userRepo.findByActivationCode(code);
//        if (user.isPresent()) {
//            user.get().setActive(true);
//            user.get().setActivationCode(null);
//            userRepo.save(user.get());
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public Optional<User> findUserByUsername(String username) {
//        return userRepo.findUserByUsername(username);
//    }
}
