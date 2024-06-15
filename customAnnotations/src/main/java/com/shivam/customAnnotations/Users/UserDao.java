package com.shivam.customAnnotations.Users;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDao {

    @JsonProperty(value = "USERNAME")
    @Column(name = "USER_NAME")
    private String userName;

    @JsonProperty(value = "EMAIL")
    @Column(name = "USER_EMAIL")
    private String email;
}
