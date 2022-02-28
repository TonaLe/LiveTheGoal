package app.security.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The type Shopping session.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "ShoppingSession")
public class ShoppingSession {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The Account.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    /**
     * The Total.
     */
    @Column(name = "total")
    private Long total;

    /**
     * The Cart items.
     */
    @OneToMany(mappedBy = "shoppingSession", fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

    /**
     * The Created at.
     */
    @Column(name = "createdAt")
    @CreatedDate
    private Date createdAt;

    /**
     * The Modified at.
     */
    @Column(name = "modifiedAt")
    @LastModifiedDate
    private Date modifiedAt;
}
