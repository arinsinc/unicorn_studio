package com.unicorn.studio.controller;



import com.unicorn.studio.entity.Role;
import com.unicorn.studio.entity.User;
import com.unicorn.studio.projection.UserProjection;
import com.unicorn.studio.service.AuthenticationService;
import com.unicorn.studio.service.UserService;
import com.unicorn.studio.utils.UserValidator;
import com.unicorn.studio.utils.ResponseSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/registration")
    public ResponseEntity<ResponseSerializer> registration(@RequestBody User user, BindingResult bindingResult, @RequestParam String role) {
        Role userRole = userService.getRoleByName(role);
        user.setRole(userRole);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            ResponseSerializer response = new ResponseSerializer(false,"conflict", bindingResult.getAllErrors().get(0).getCode(),null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        ResponseSerializer response = new ResponseSerializer(true,"success","Your account is created successfully. Check your email for account confirmation", null);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseSerializer> login() {
        ResponseSerializer response = new ResponseSerializer(false,"conflict","Login before continuing", null);
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/email-confirmation")
    public ResponseEntity<ResponseSerializer> emailConfirmation(@RequestParam String email, String token) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getConfirmationToken().equals(token)) {
            userService.emailConfirmation(email,token);
            ResponseSerializer response = new ResponseSerializer(true,"success","Your account is verified", null);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        ResponseSerializer response = new ResponseSerializer(false,"error","Invalid token", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/reset-password-token")
    public ResponseEntity<ResponseSerializer> resetPasswordToken(@RequestBody String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            ResponseSerializer response = new ResponseSerializer(false,"error","This email address is not registered with us", null);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        userService.resetPasswordTokenGeneration(email);
        ResponseSerializer response = new ResponseSerializer(true,"success","Password reset link has been send to your registered email", null);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/reset-password-token-validation")
    public ResponseEntity<ResponseSerializer> resetPasswordTokenValidation(@RequestParam String email, String token) {
        boolean isValid = userService.validateResetPasswordToken(email,token);
        ResponseSerializer response = new ResponseSerializer(true, "success", "Token is verified", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseSerializer> resetPassword(@RequestBody String email, String token, String password) {
        User user = userService.getUserByEmail(email);
        if (user != null && user.getPasswordResetToken().equals(token)) {
            userService.resetPassword(email,token,password);
            ResponseSerializer response = new ResponseSerializer(true,"success","Your password has been changed successfully", null);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        ResponseSerializer response = new ResponseSerializer(false,"error","Invalid token", null);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<ResponseSerializer> changePassword(@RequestBody Object obj, @PathVariable String userId) {
        User user = userService.changePassword(obj, userId);
        if (user == null) {
            ResponseSerializer response = new ResponseSerializer(false,"error","There is some error", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        ResponseSerializer response = new ResponseSerializer(true,"success","Your password has been changed successfully", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseSerializer> getUsers() {
        List<User> userList = userService.getUsers();
        ResponseSerializer response = new ResponseSerializer(true,"success","Users fetched successfully", userList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseSerializer> getUser(@PathVariable String userId) {
        UserProjection user = userService.getUser(userId);
        ResponseSerializer response = new ResponseSerializer(true,"success","User fetched successfully", user);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseSerializer> addUser(@RequestBody User user) {
        user.setId(0);
        userService.saveUser(user);
        ResponseSerializer response = new ResponseSerializer(true,"success","User added successfully", user);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<ResponseSerializer> updateUser(@RequestBody User user) {
        userService.saveUser(user);
        ResponseSerializer response = new ResponseSerializer(true,"success","User updated successfully", user);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseSerializer> deleteUser(@PathVariable String userId) {
        UserProjection isUser = userService.getUser(userId);
        if (isUser == null) {
            ResponseSerializer response = new ResponseSerializer(false,"error","User not found", null);
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(userId);
        ResponseSerializer response = new ResponseSerializer(true,"success","User deleted successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/user-session")
    public ResponseEntity<ResponseSerializer> userSession() {
        ResponseSerializer response = new ResponseSerializer(true,"success","You have been logged in successfully", null);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/invalid-session")
    public ResponseEntity<ResponseSerializer> invalidSession() {
        ResponseSerializer response = new ResponseSerializer(false,"error","Invalid email or password",null);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/authenticate-user")
    public ResponseEntity<ResponseSerializer> authenticateUser() {
        UserProjection user = authenticationService.getAuthenticatedUserByEmail();
        LinkedHashMap<String, Object> authenticatedUser = new LinkedHashMap<>();
        if (user != null) {
            authenticatedUser.put("user", user);
            authenticatedUser.put("role", user.getRole().getName());
            ResponseSerializer response = new ResponseSerializer(true,"success","User is authenticated", authenticatedUser);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        ResponseSerializer response = new ResponseSerializer(true,"success","Unauthenticated user",null);
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }
}
