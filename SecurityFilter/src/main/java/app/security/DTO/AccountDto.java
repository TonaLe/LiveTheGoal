package app.security.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

/**
 * The type Account dto.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {

    /**
     * The Username.
     */
    @NotNull(message = "Username cannot be null")
    @Size(min = 6, message = "Username must be above 6 characters")
    private String username;
    /**
     * The Password.
     */
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be above 6 characters")
    private String password;
    /**
     * The First name.
     */
    @NotNull(message = "firstName cannot be null")
    @Size(min = 6, message = "firstName must be above 6 characters")
    private String firstName;
    /**
     * The Last name.
     */
    @NotNull(message = "lastName cannot be null")
    @Size(min = 6, message = "lastName must be above 6 characters")
    private String lastName;
    /**
     * The Msisdn.
     */
    private String msisdn;
    /**
     * The Email.
     */
    @Email
    private String email;
    /**
     * The Role.
     */
    private String role;

    /**
     * The Address.
     */
    private String address;

    /**
     * The City.
     */
    private String city;

    /**
     * The Country.
     */
    private String country;
}
