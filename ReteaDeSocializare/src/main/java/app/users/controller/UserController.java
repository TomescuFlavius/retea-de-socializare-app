package app.users.controller;
import app.users.dtos.UserCreateRequest;
import app.users.dtos.UserResponse;
import app.users.dtos.UserResponseList;
import app.users.dtos.UserUpdateRequest;
import app.users.exceptions.UserAlreadyExistException;
import app.users.exceptions.UserNotFoundException;
import app.users.service.UserCommandService;
import app.users.service.UserQueryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    private UserQueryService userQueryService;
    private UserCommandService userCommandService;
    public UserController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @GetMapping("/all")
    public ResponseEntity<UserResponseList> findAllUsers() {
        log.info("HTTP get /api/v1/users");
        return ResponseEntity.status(HttpStatus.OK).body(userQueryService.findAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) throws UserAlreadyExistException {
        log.info("HTTP POST /api/v1/users/add/ username={}, password={}, createdAt={}", userCreateRequest.username(), userCreateRequest.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(userCommandService.createUser(userCreateRequest));
    }

    @PutMapping("/update/{email}")
        public ResponseEntity<UserResponse> updateUser(@PathVariable String email, @RequestBody UserUpdateRequest userUpdateRequest) throws UserNotFoundException {
        log.info("HTTP PUT /api/v1/users/update/{} username={}, email={}, password={}",email, userUpdateRequest.username(), userUpdateRequest.email(), userUpdateRequest.password());
        return ResponseEntity.ok(userCommandService.updateUser(email, userUpdateRequest));
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String email) throws UserNotFoundException {
        log.info("HTTP DELETE /api/v1/users/delete/{}",email);
        return ResponseEntity.status(HttpStatus.OK).body(userCommandService.deleteUser(email));
    }

}
