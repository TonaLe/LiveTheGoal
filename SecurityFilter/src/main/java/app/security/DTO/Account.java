package app.security.DTO;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "createdAt")
    @CreatedDate
    private String createdAt;

    @Column(name = "modifiedAt")
    @LastModifiedDate
    private String modifiedAt;
}
