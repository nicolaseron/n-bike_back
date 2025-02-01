package com.example.n_bike.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_seq")
    @SequenceGenerator(name = "purchase_order_seq", sequenceName = "purchase_order_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "command_number", nullable = false, unique = true, length = 20)
    private String commandNumber;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @PrePersist
    public void generateCommandNumber() {
        this.commandNumber = "CMDA" + this.id;
    }
}