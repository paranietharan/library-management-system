package com.alphacodes.librarymanagementsystem.DTO;

import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private Role role;

    @Lob
    @Column(name = "profile_img", columnDefinition = "LONGBLOB")
    private byte[] profileImg;

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .emailAddress(user.getEmailAddress())
                .role(user.getRole())
                .profileImg(user.getProfileImg())
                .build();
    }

    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .emailAddress(emailAddress)
                .password(password)
                .role(role)
                .profileImg(profileImg)
                .build();
    }
}