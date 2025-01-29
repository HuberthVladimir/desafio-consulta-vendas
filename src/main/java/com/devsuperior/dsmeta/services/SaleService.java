package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	private LocalDate parseDateOrDefault(String dateString, LocalDate defaultDate) {
		return (dateString == null || dateString.isEmpty()) ? defaultDate
				: LocalDate.parse(dateString);
	}

	private LocalDate[] getDefaultDateRange(String minDate, String maxDate) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate oneYearAgo = today.minusYears(1L);

		LocalDate startDate = this.parseDateOrDefault(minDate, oneYearAgo);
		LocalDate endDate = this.parseDateOrDefault(maxDate, today);

		return new LocalDate[] {startDate, endDate};
	}

	public Page<SalesReportDTO> searchSalesReport(Pageable page, String minDate, String maxDate,
			String name) {
		LocalDate[] dateRange = getDefaultDateRange(minDate, maxDate);
		LocalDate startDate = dateRange[0];
		LocalDate endDate = dateRange[1];

		String searchName = (name == null || name.isEmpty()) ? "" : name;

		return repository.searchSalesReport(page, startDate, endDate, searchName);
	}

	public List<SalesSummaryDTO> searchSalesSummary(String minDate, String maxDate) {
		LocalDate[] dateRange = this.getDefaultDateRange(minDate, maxDate);
		LocalDate startDate = dateRange[0];
		LocalDate endDate = dateRange[1];

		return repository.searchSummarySales(startDate, endDate).stream().map(SalesSummaryDTO::new)
				.collect(Collectors.toList());
	}
}
