package com.bridgelabz.bookstorebackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class UserDTO {

    @Pattern(regexp="^[A-Z]{1}[a-zA-Z\\s]{2,}$",message="Employee firstName is Not valid")
    public String firstName;

    @Pattern(regexp="^[A-Z]{1}[a-zA-Z\\s]{2,}$",message="Employee lastName is Not valid")
    public String lastName;

    @Email
    private String email;

    @NotEmpty(message = "address can not be empty")
    private String kyc;

    @NotEmpty(message = "Password cant be empty")
    private String password;

//    @JsonFormat(pattern="dd MMM yyyy")
    private LocalDate dob;

//    @JsonFormat(pattern="dd MMM yyyy")
    private LocalDate registeredDate;

}
