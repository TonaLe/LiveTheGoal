package app.security.Entity;

import app.security.Enum.Role;
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
@Entity(name = "product")
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

    @Column(name = "categoryid")
    private int categoryid;

    @Column(name = "brandid")
    private int brandid;

    @Column(name = "inventoryid")
    private int inventoryid;

    @Column(name = "isactive")
    private boolean isactive;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "inventoryid")
    private Inventory inventory;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "inventoryid")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "categoryid") // thông qua khóa ngoại categoryid
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Category category;


    @Column(name = "createdat")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;

    @Column(name = "modifiedat")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedat;
}