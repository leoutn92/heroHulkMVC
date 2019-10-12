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
@Table(name="hero_mst", schema = "public")
public class Hero {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;
    @Column(unique = true)
    private String name;
    @Column
    private String trade;
    @OneToMany(mappedBy = "hero")
    private Set<Article> articles;
}
