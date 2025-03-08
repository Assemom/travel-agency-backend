package com.travel.management.service;

import com.travel.management.exception.EmailFailedToSendException;
import com.travel.management.exception.IncorrectVerificationCodeException;
import com.travel.management.model.RegistrationObject;
import com.travel.management.model.Role;
import com.travel.management.model.User;
import com.travel.management.exception.EmailAlreadyExistException;
import com.travel.management.exception.UserDoesNotExistException;
import com.travel.management.repository.RoleRepository;
import com.travel.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GmailService gmailService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,GmailService gmailService,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.gmailService = gmailService;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegistrationObject registrationObject){

        User user = new User();
        user.setFirstName(registrationObject.getFirstName());
        user.setLastName(registrationObject.getLastName());
        user.setEmail(registrationObject.getEmail());
        user.setPassword(registrationObject.getPassword());

        //set up the default role to be ROLE_TOURIST
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findByRoleType(Role.RoleType.ROLE_TOURIST).get());
        user.setRoles(roles);
        //if we entered an existed email it will throw an exception we handle it in the controller
        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new EmailAlreadyExistException();
        }
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User addUser(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default values
        user.setEnabled(true);

        // Handle roles from JSON
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Role> roles = user.getRoles().stream()
                    .map(role -> roleRepository.findById(role.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Role with ID " + role.getId() + " not found")))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        } else {
            // Assign a default role if none provided (e.g., ROLE_TOURIST)
            Role defaultRole = roleRepository.findByRoleType(Role.RoleType.ROLE_TOURIST)
                    .orElseThrow(() -> new IllegalArgumentException("Default role ROLE_TOURIST not found"));
            user.setRoles(new HashSet<>(Set.of(defaultRole)));
        }

        // Save and return the user
        return userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);
    }
    public User updateUser(User user) {
        try {
            return userRepository.save(user);
        }catch (Exception e){
            throw new UserDoesNotExistException();
        }
    }
    public User setPassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);
        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }
    public void getVerificationCode(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);

        user.setVerification(generateVerificationNumber());
        try {
            gmailService.sendEmail(user.getEmail(),"Your verification code" , "here is tour verification code : "+user.getVerification());
            userRepository.save(user);
        } catch (Exception e) {
            throw new EmailFailedToSendException(e.getMessage());
        }
        userRepository.save(user);
    }
    private long generateVerificationNumber() {
        return (long) Math.floor(Math.random() * 100_000_000);
    }

    public User verifyEmail(String email, Long code) {
        User user = userRepository.findByEmail(email).orElseThrow(UserDoesNotExistException::new);
        if(code.equals(user.getVerification())){
            user.setEnabled(true);
            user.setVerification(0);
            return userRepository.save(user);
        }else {
            throw new IncorrectVerificationCodeException();
        }
    }


}
