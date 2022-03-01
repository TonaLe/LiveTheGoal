package app.security.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "describe")
    private String describe;

    @Column(name = "price")
    private float price;

    @Column(name = "sku")
    private String sku;

    @Column(name = "isAvailable")
    private boolean isAvailable;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "inventoryId")
    private Inventory inventory;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId") // thông qua khóa ngoại categoryid
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    @PrimaryKeyJoinColumn
    private CartItem cartItem;

    @Column(name = "createdAt")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "modifiedAt")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
}
