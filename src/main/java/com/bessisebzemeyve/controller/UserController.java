package com.bessisebzemeyve.controller;

import com.bessisebzemeyve.model.SaveUserRequestDTO;
import com.bessisebzemeyve.model.UpdatePasswordRequestDTO;
import com.bessisebzemeyve.model.UpdateUserRequestDTO;
import com.bessisebzemeyve.model.UserResponseDTO;
import com.bessisebzemeyve.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id){
        LOGGER.info("A get user request has been sent with {} from an admin.", id);
        UserResponseDTO userResponseDTO = userService.getUser(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> searchAllUsers(
                                                                @RequestParam(name = "name", defaultValue = "") String username){
        LOGGER.info("A search request has been sent with username: {} from an admin.", username);
        List<UserResponseDTO> userResponseDTOPage = userService.searchUser(username);
        return ResponseEntity.ok(userResponseDTOPage);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody SaveUserRequestDTO dto){
        LOGGER.info("An user register request has been sent with name: {}", dto.getUserName());
        UserResponseDTO userResponseDTO = userService.saveUser(dto);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable long id){
        LOGGER.info("A delete user request has been sent");
        UserResponseDTO userResponseDTO = userService.deleteUser(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserRequestDTO dto, @PathVariable long id) {
        LOGGER.info("An update user request has been sent");
        UserResponseDTO userResponseDTO = userService.updateUser(dto, id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/password-update/{id}")
    @PreAuthorize("hasRole('ROLE_TRAINER') ")
    public ResponseEntity<UserResponseDTO> updatePassword(@PathVariable long id, @Valid @RequestBody UpdatePasswordRequestDTO dto){
        LOGGER.info("An update password request has been sent");
        UserResponseDTO userResponseDTO = userService.updatePassword(dto, id);
        return ResponseEntity.ok(userResponseDTO);
    }

}