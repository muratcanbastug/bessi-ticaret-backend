package com.bessisebzemeyve.service;

import com.bessisebzemeyve.entity.User;
import com.bessisebzemeyve.model.*;
import com.bessisebzemeyve.repository.OrderRepository;
import com.bessisebzemeyve.repository.RoleRepository;
import com.bessisebzemeyve.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;

    public UserService(OrderRepository orderRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.orderRepository = orderRepository;
    }

    private User getUserByName() {
        String name = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName).orElseThrow(() -> new BadCredentialsException("Bad Credentials"));
        return userRepository.findByUsername(name);
    }

    public UserResponseDTO getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with given id."));
        return generateResponse(user);
    }

    public UserRoleResponseDTO getUserRole(){
        User user = getUserByName();
        UserRoleResponseDTO userRoleResponseDTO = new UserRoleResponseDTO();
        userRoleResponseDTO.setRole(user.getRole().getName());
        return userRoleResponseDTO;
    }

    public UserResponseDTO saveUser(SaveUserRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUserName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        var roleUserOpt = roleRepository.findByName("ROLE_CUSTOMER");
        roleUserOpt.ifPresent(user::setRole);
        user.setName(dto.getName());
        user.setAddress(dto.getAddress());
        user.setPhone_number(dto.getPhoneNumber());
        User savedUser = userRepository.save(user);
        return generateResponse(savedUser);
    }

    public List<UserResponseDTO> searchUser(String name) {
        List<User> users = userRepository.findAllByNameContainsOrderByName(name);
        return users.stream().map(this::generateResponse).toList();
    }

    public UserResponseDTO deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Given id does not exist."));
        orderRepository.deleteAllByUser_Id(id);
        userRepository.deleteById(id);
        return generateResponse(user);
    }

    public UserResponseDTO updateUser(UpdateUserRequestDTO dto, long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Given id does not exist."));
        user.setUsername(dto.getUserName());
        user.setName(dto.getName());
        user.setAddress(dto.getAddress());
        user.setPhone_number(dto.getPhoneNumber());
        User savedUser = userRepository.save(user);
        return generateResponse(savedUser);
    }

    public UserResponseDTO updatePassword(UpdatePasswordRequestDTO dto, long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Given id does not exist."));
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        User savedUser = userRepository.save(user);
        return generateResponse(savedUser);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        return new MyUserDetail(user);
    }

    private UserResponseDTO generateResponse(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName(user.getName());
        userResponseDTO.setId(user.getId());
        userResponseDTO.setAddress(user.getAddress());
        userResponseDTO.setUserName(user.getUsername());
        userResponseDTO.setPhone(user.getPhone_number());
        userResponseDTO.setRole(user.getRole().getName());
        return userResponseDTO;
    }

}
