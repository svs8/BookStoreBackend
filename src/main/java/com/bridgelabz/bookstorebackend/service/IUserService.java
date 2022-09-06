package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.dto.UserDTO;
import com.bridgelabz.bookstorebackend.model.UserRegistration;

import java.util.Optional;


public interface IUserService {


    Integer registerUser(UserDTO userDTO);

    int verifyOtp(String email, Integer otp);

    int loginUser(String email, String password);

    Optional<UserRegistration> getUserDataByToken(String token);

    UserRegistration updateRecordByToken(String token, UserDTO userDTO);

    UserRegistration getUserDataById(int userId);

    UserRegistration deleteRecordByToken(String token);

    String forgotPassword(String email, String password);

    String getToken(String email);

    Object getIdByToken(String token);

    UserRegistration updateRecordById(Integer id, UserDTO userDTO);



}
