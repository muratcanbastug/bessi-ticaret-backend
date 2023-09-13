package com.bessisebzemeyve.service;

import com.bessisebzemeyve.entity.User;
import com.bessisebzemeyve.model.*;
import com.bessisebzemeyve.repository.RoleRepository;
import com.bessisebzemeyve.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public UserResponseDTO getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No user with given id."));
        return generateResponse(user);
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

    public List<UserResponseDTO> searchUser(String username) {
        List<User> users = userRepository.findAllByUsernameContainsOrderByUsername(username);
        return users.stream().map(this::generateResponse).toList();
    }

    public UserResponseDTO deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Given id does not exist."));
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
        userResponseDTO.setName(user.getUsername());
        userResponseDTO.setId(user.getId());
        userResponseDTO.setAddress(user.getAddress());
        userResponseDTO.setUserName(user.getUsername());
        userResponseDTO.setPhone(user.getPhone_number());
        userResponseDTO.setRole(user.getRole().getName());
        return userResponseDTO;
    }

}
