package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.dto.UserDTO;
import com.bridgelabz.bookstorebackend.model.UserRegistration;

import java.util.Optional;


public interface IUserService {


    UserRegistration registerUser(UserDTO userDTO);

    Boolean verifyOtp(String email, Integer otp);

    String loginUser(String email, String password);

    Optional<UserRegistration> getUserDataByToken(String token);

    UserRegistration updateRecordByToken(String token, UserDTO userDTO);

    UserRegistration getUserDataById(int userId);

    UserRegistration deleteRecordByToken(String token);

    String forgotPassword(String email, String password);

}
