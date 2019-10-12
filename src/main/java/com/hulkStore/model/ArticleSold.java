package com.hulkStore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="article_sold_mst", schema = "public")
// EL objetivo de esta clase es persistir informacion acerca de cuanta cantidad y a que precio se vendio un articulo
public class ArticleSold {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int articleSoldCode;
    @ManyToOne
    @JoinColumn(name="sale_id", nullable=false)
    private Sale sale ;
    @ManyToOne
    @JoinColumn(name="article_id", nullable=false)
    private Article article;
    @Column
    private int amountSold;
    @Column
    private float unitPrice;
    @Transient
    private int articleCode;
    @Transient
    public float getTotalPrice() {
        return amountSold*unitPrice;
    }
}
