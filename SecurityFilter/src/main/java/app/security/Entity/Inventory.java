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
@Entity(name = "productinventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private String quantity;

    @OneToOne(mappedBy = "inventory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Product product;

    @Column(name = "createdat")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;

    @Column(name = "modifiedat")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedat;
}
