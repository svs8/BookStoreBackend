package com.bridgelabz.bookstorebackend.controller;


import com.bridgelabz.bookstorebackend.dto.ResponseDTO;
import com.bridgelabz.bookstorebackend.dto.UserDTO;
import com.bridgelabz.bookstorebackend.dto.UserLoginDTO;
import com.bridgelabz.bookstorebackend.model.UserRegistration;
import com.bridgelabz.bookstorebackend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserRegistrationController {

    @Autowired
    IUserService userRegistrationService;


    final static String SUCCESS = "Entered Otp is valid, and Registration was successful.";
    final static String FAIL = "Entered OTP was not valid!, Registration failed!, please try again";

    @GetMapping("/")
    public String hello() {
        return "welcome to user registration page";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUserInBookStore(@Valid @RequestBody UserDTO userDTO) {
        Integer userRegistration = userRegistrationService.registerUser(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User Registered Successfully", userRegistration);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping({"/verifyotp"})
    public int verifyOtp(@RequestParam String email, @RequestParam Integer otp) {
        int isVerifyOtp = userRegistrationService.verifyOtp(email, otp);
       return isVerifyOtp;
    }


    @GetMapping("/login")
    public int userLogin(@RequestParam String email, @RequestParam String password) {
        UserLoginDTO userLoginDTO=new UserLoginDTO(email, password);
        int response = userRegistrationService.loginUser(userLoginDTO.getEmail(),userLoginDTO.getPassword());
        return response;
    }

    @GetMapping("/getToken/{email}")
    public ResponseEntity<ResponseDTO> getToken(@PathVariable String email){
        String generatedToken=userRegistrationService.getToken(email);
        ResponseDTO responseDTO=new ResponseDTO("Token for mail id sent on mail successfully",generatedToken);
        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/getByToken/{token}")
    public ResponseEntity<ResponseDTO> getUserDataByToken(@PathVariable String token)
    {
        Optional<UserRegistration> user = userRegistrationService.getUserDataByToken(token);
        ResponseDTO responseDTO = new ResponseDTO("Data retrieved successfully (:",user);
        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }

    @PutMapping("/updateByToken/{token}")
    public ResponseEntity<String> updateRecordById(@PathVariable String token,@Valid @RequestBody UserDTO userDTO){
        UserRegistration entity = userRegistrationService.updateRecordByToken(token,userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User Record updated successfully",entity);
        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = {"/deleteByToken/{token}"})
    public ResponseEntity<ResponseDTO> deleteRecordByToken(@PathVariable String token) {
        UserRegistration entity = userRegistrationService.deleteRecordByToken(token);
        ResponseDTO responseDTO = new ResponseDTO("Data Deleted Successfully!!!",
                "ID number: "+entity.getUserId() + " DELETED!!!");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/forgotpassword")
    public String forgotPassword(@RequestParam String email, @RequestParam String newPassword) {
        return userRegistrationService.forgotPassword(email, newPassword);
    }

    //Get user by user token from where we can extract ID
    @GetMapping("/getIdByToken/{token}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String token) {
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get UserId By Token",
                userRegistrationService.getIdByToken(token)), HttpStatus.OK);
    }


}
