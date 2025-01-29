package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.entities.Seller;

public class SalesReportDTO {

    private Long id;
    private LocalDate date;
    private Double amount;
    private String name;

    public SalesReportDTO() {}

    public SalesReportDTO(Long id, LocalDate date, Double amount, String name) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.name = name;
    }

    public SalesReportDTO(Sale sale, Seller seller) {
        id = sale.getId();
        date = sale.getDate();
        amount = sale.getAmount();
        name = seller.getName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

}
