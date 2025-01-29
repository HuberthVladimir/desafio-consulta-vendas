package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.entities.Sale;

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
}
