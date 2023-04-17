package com.example.panda3.entity.user;

import jakarta.persistence.*;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
@Entity
@Table(name= "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    private String firstName;

    @Size(min = 1, max = 20, message = "Surname must be between 1 and 20 characters")
    private String lastName;

    @NonNull
    @Email(message = "Invalid email format")
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", message = "Invalid email characters")
    private String email;

    @NonNull
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
  //  @Pattern(regexp = "[a-zA-Z0-9._%+-]+[\\-\\_]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}",
 //           message = "Invalid password format. The password should be between 8-20 characters. Use at least one lowercase letter, one uppercase letter, one number, and one special character.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Password must be between 8 and 20 characters, contain at least one uppercase letter, one lowercase letter, one number, and one special character: @$!%*?&")
    private String password;


    public UserEntity(Long id, String firstName, String lastName, String email, String password) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserEntity() {

    }

    public void setFirstName(String firstName) {
        try {
            this.firstName = Objects.requireNonNull(firstName, "First name cannot be null");
        } catch (NullPointerException e) {
            // Obsługa wyjątku
        }
    }
}
