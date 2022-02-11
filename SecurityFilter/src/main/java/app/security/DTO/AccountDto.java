package app.security.DTO;

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
     * The Id.
     */
    private int id;
    /**
     * The Username.
     */
    private String username;
    /**
     * The Password.
     */
    private String password;
    /**
     * The First name.
     */
    private String firstName;
    /**
     * The Last name.
     */
    private String lastName;
    /**
     * The Msisdn.
     */
    private String msisdn;
    /**
     * The Email.
     */
    private String email;
    /**
     * The Role.
     */
    private String role;
}
