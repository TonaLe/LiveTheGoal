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
@Entity(name = "CartItem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sessionId", nullable = false)
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cart")
    @PrimaryKeyJoinColumn
    private Product product;

    private int quantity;

    @Column(name = "createdAt")
    @CreatedDate
    private Date createdAt;

    @Column(name = "modifiedAt")
    @LastModifiedDate
    private Date modifiedAt;
}
