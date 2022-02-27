package app.security.Entity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "productcategory")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "createdat")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) // Quan hệ 1-n (1 category có nhiều product thuộc)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    private Collection<Product> products;

    @Column(name = "modifiedat")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedat;
}
