package com.travel.management.controller;

import com.travel.management.exception.EmailFailedToSendException;
import com.travel.management.exception.IncorrectVerificationCodeException;
import com.travel.management.model.RegistrationObject;
import com.travel.management.model.User;
import com.travel.management.exception.EmailAlreadyExistException;
import com.travel.management.exception.UserDoesNotExistException;
import com.travel.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }
    @ExceptionHandler({EmailAlreadyExistException.class})
    //handling the exception of email is existed by send status conflict to the response
    public ResponseEntity<String> handleEmailTaken(){
        return new ResponseEntity<String>("Email you provided is already in use ", HttpStatus.CONFLICT);
    }
    @ExceptionHandler({UserDoesNotExistException.class})
    //handling the exception of email is existed by send status conflict to the response
    public ResponseEntity<String> handleUserIsNotExist(){
        return new ResponseEntity<String>("User you are looking for doesn't exist ", HttpStatus.NOT_FOUND);
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationObject userObject){

        return userService.registerUser(userObject);
    }
    @PutMapping("/update/phone")
    public User updatePhoneNumber(@RequestBody LinkedHashMap<String,String> body){
        String email = body.get("email");
        String phone = body.get("phoneNumber");
        User user = userService.getUserByEmail(email);
        user.setPhoneNumber(phone);

        return userService.updateUser(user);
    }
    @PutMapping("/update/password")
    public User updatePassword(@RequestBody LinkedHashMap<String,String> body){
        String email = body.get("email");
        String password = body.get("password");

        return userService.setPassword(email,password);
    }
    @ExceptionHandler({EmailFailedToSendException.class})
    public ResponseEntity<String> handleFailedEmail(){
        return new ResponseEntity<String>("Email failed to send try again",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/email/code")
    public ResponseEntity<String> generateVerificationCode(@RequestBody LinkedHashMap<String,String> body){
        userService.getVerificationCode(body.get("email"));
        return new ResponseEntity<String>("Verification code generated, email sent",HttpStatus.OK);
    }
    @ExceptionHandler({IncorrectVerificationCodeException.class})
    public ResponseEntity<String> incorrectCodeHandler(){
        return new ResponseEntity<String>("the code doesnnot match the send code",HttpStatus.CONFLICT);
    }
    @PostMapping("/email/verify")
    public User verifyEmail(@RequestBody LinkedHashMap<String,String> body){
        Long code = Long.parseLong(body.get("code"));
        String email = body.get("email");

        return userService.verifyEmail(email,code);
    }
}
