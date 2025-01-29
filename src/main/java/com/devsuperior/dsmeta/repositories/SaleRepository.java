package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SalesSummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("""
            SELECT new com.devsuperior.dsmeta.dto.SalesReportDTO(
                s.id, s.date, s.amount, se.name)
            FROM Sale s
            JOIN s.seller se
            WHERE s.date BETWEEN :minDate AND :maxDate
                AND (:name = '' OR LOWER(se.name) LIKE LOWER(CONCAT('%', :name, '%')))
            """)
    Page<SalesReportDTO> searchSalesReport(Pageable page, LocalDate minDate, LocalDate maxDate,
            String name);

    @Query(nativeQuery = true, value = """
              SELECT SUM(TB_SALES.AMOUNT) AS total, TB_SELLER.NAME AS sellerName
              FROM TB_SALES
              INNER JOIN TB_SELLER ON TB_SALES.SELLER_ID = TB_SELLER.ID
              WHERE TB_SALES.DATE BETWEEN :minDate AND :maxDate
              GROUP BY TB_SELLER.NAME
            """)
    List<SalesSummaryProjection> searchSummarySales(LocalDate minDate, LocalDate maxDate);
}
