package com.hulkStore.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sale_mst", schema = "public")
public class Sale {
    @Transient
    static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int saleCode;
    @Column
    @CreationTimestamp
    private Date saleDate;
    @Column
    private float totalSalePrice;
    @OneToMany(mappedBy = "sale")
    private Set<ArticleSold> articleSolds;
    @Transient
    public String getTransactionDateFormatted() {
        return df.format(saleDate);
    }
}
