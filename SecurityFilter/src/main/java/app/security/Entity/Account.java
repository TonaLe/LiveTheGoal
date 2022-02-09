package app.security.Entity;

import lombok.*;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Account")
public class Account implements Serializable {
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
    @Temporal(TemporalType.TIMESTAMP)
    private String createdAt;

    @Column(name = "modifiedAt")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private String modifiedAt;
}
