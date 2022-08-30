package com.bridgelabz.bookstorebackend.service;

import com.bridgelabz.bookstorebackend.Util.EmailSenderService;
import com.bridgelabz.bookstorebackend.Util.TokenUtility;
import com.bridgelabz.bookstorebackend.dto.UserDTO;
import com.bridgelabz.bookstorebackend.model.UserRegistration;
import com.bridgelabz.bookstorebackend.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
public class UserService implements IUserService {

    @Autowired
    EmailSenderService emailService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRegistrationRepository iUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtility tokenUtility;


    @Override
    public UserRegistration registerUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserRegistration user = modelMapper.map(userDTO, UserRegistration.class);
        String otp = generateRandomNumberString();
        Integer intOtp = Integer.parseInt(otp);
        user.setOtp(intOtp);
        iUserRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Otp for Verification", "Hi" + user.getFirstName() + " Otp sent for verification purpose" + intOtp);
        return user;
    }

    @Override
    public int verifyOtp(String email, Integer otp) {
        UserRegistration user = iUserRepository.findByEmailId(email);
        if (user == null)
            return 0;
        Integer serverOtp = user.getOtp();
        if (!(otp.equals(serverOtp)))
            return 2;
        iUserRepository.changeVerified(email);
        emailService.sendEmail(user.getEmail(), "Verification Successful", "Hi " + user.getFirstName() + ", You have successfully " +
                "verified your account. You can now login using Your email and password ");
        return 1;
    }


//    @Override
//    public String loginUser(String email, String password) {
//
//        UserRegistration login = iUserRepository.findByEmailId(email);
//        if (login != null) {
//            if (login.getVerify() != null) {
//                String pass = login.getPassword();
//                System.out.println(pass);
//                System.out.println(password);
//                if (passwordEncoder.matches(password, login.getPassword())) {
//                    return "User Login successfully and the token is: \n " + getToken(login.getEmail());
//                } else {
//                    return "Wrong Password";
//                }
//            }
//            return "User is not Verified please verify and try to login";
//        }
//        return "User not found";
//    }

    @Override
    public int loginUser(String email, String password) {

        UserRegistration login = iUserRepository.findByEmailId(email);
        if (login != null) {
            if (login.getVerify() != null) {
                String pass = login.getPassword();
                System.out.println(pass);
                System.out.println(password);
                if (passwordEncoder.matches(password, login.getPassword())) {
                    return 1; //successful
                } else {
                    return 2; //incorrect password
                }
            }
            return 3;  //User not verified
        }
        return 0;
    }

    public String getToken(String email) {
        UserRegistration user = iUserRepository.findByEmailId(email);
        String token = tokenUtility.createToken(user.getUserId());
        return token;
    }

    public static String generateRandomNumberString() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public Optional<UserRegistration> getUserDataByToken(String token) {
        int id = tokenUtility.decodeToken(token);
        Optional<UserRegistration> login = iUserRepository.findById(id);
        return login;
    }

    @Override
    public UserRegistration updateRecordByToken(String token, UserDTO userDTO) {
        Integer id = tokenUtility.decodeToken(token);
        UserRegistration user = this.getUserDataById(id);
        modelMapper.map(userDTO,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        iUserRepository.save(user);
        return user;
    }

    @Override
    public UserRegistration getUserDataById(int userId) {
        return iUserRepository.findByUserId(userId);
    }

    @Override
    public UserRegistration deleteRecordByToken(String token) {
        Integer id = tokenUtility.decodeToken(token);
        UserRegistration user = this.getUserDataById(id);
        iUserRepository.delete(user);
        return user;
        }

    @Override
    public String forgotPassword(String email, String password) {
        UserRegistration user = iUserRepository.findByEmailId(email);
        if (user == null)
            return "User not found";
        String newPassword = passwordEncoder.encode(password);
        System.out.println("newPassword: " + newPassword);
        iUserRepository.updateNewPassword(email, newPassword);
        return "Password updated successfully";
    }

    }




