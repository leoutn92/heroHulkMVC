package com.hulkStore.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction_mst", schema = "public")
//El objetivo de esta clase es registrar fecha y motivo de los cambios en el stock y precio de los articulos
public class Transaction {
    @Transient
    static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    @JoinColumn(name="article_id", nullable=false)
    private Article article;
    @Column
    private float priceDifference;
    @Column
    private int stockDifference;
    @Column
    private float price;
    @Column
    private int stock;
    @Column
    private String type;
    @Column
    @CreationTimestamp
    private Date transactionDate;
    @Column
    private String reason;
    @Transient
    public String getTransactionDateFormatted() {
        return df.format(transactionDate);
    }
}
