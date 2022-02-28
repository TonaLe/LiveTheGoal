package app.security.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * The type Cart item.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "CartItem")
public class CartItem {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The Shopping session.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sessionId", nullable = false)
    private ShoppingSession shoppingSession;

    /**
     * The Product.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", nullable = false)
    @MapsId
    private Product product;

    /**
     * The Quantity.
     */
    private int quantity;

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
