package com.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 1,max = 30,message = "Not a valid name pattern")
    private String name;

    //@Email(message = "Not a valid emailId")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|org|in)$", message = "Email not valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @Size(min = 4, max = 6, message = "Gender not valid")
    private String gender;

    @NotBlank(message = "Please write something about you")
    private String about;

    private String imageName;
}
