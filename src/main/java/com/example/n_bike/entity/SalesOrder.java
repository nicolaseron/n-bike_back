package com.example.n_bike.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_order")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_order_seq")
    @SequenceGenerator(name = "sales_order_seq", sequenceName = "sales_order_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "command_number", nullable = false, unique = true, length = 20)
    private String commandNumber;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brands brand;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @PrePersist
    public void generateCommandNumber() {
        this.commandNumber = "CMDV" + this.id;
    }

}