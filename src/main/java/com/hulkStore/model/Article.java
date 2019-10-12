package com.hulkStore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="article_mst", schema = "public")
// El objetico de esta clase es persistir todo lo relacionado a un articulo que puede ser vendido en hulkStore
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int articleCode;
    @ManyToOne
    @JoinColumn(name="hero_id", nullable=false)
    private Hero hero;
    @Column
    private String articleType;
    @Column
    private String size;
    @Column
    private int stock;
    @Column
    private float price;
    @Column
    private String description;
    @OneToMany(mappedBy = "article")
    private Set<Transaction> transaction;
    @OneToMany(mappedBy = "article")
    private Set<ArticleSold> articleSolds;
}
