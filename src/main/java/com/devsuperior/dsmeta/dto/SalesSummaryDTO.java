package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SalesSummaryProjection;

public class SalesSummaryDTO {

    private String sellerName;
    private Double total;

    public SalesSummaryDTO() {}

    public SalesSummaryDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SalesSummaryDTO(SalesSummaryProjection salesSummary) {
        total = salesSummary.getTotal();
        sellerName = salesSummary.getSellerName();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }


}
