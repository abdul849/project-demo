package exmple.controller;
import exmple.entity.Role;
import exmple.entity.User;
import exmple.payload.SingUpDto;
import exmple.repository.RoleRepository;
import exmple.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private RoleRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/singnup")
    public ResponseEntity<?> registerUser(@RequestBody SingUpDto singUpDto){
        if(userRepository.existsByUsername(singUpDto.getUsername())){
            return new ResponseEntity<>("Username is Already teken",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(singUpDto.getEmail())){
            return new ResponseEntity<>("Email is Already teken",HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setName(singUpDto.getName());
        user.setEmail(singUpDto.getEmail());
        user.setUsername(singUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(singUpDto.getPassword()));

        Role role=repository.findByName(singUpDto.getRoleType()).get();
        Set<Role> convertRoleToSet=new HashSet<>();
        convertRoleToSet.add(role);
        user.setRoles(convertRoleToSet);


        userRepository.save(user);


        return new ResponseEntity<>("User reister successfully",HttpStatus.OK);
    }
}
