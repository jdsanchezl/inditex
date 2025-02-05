package com.inditex.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.inditex.domain.model.Prices;

public interface PricesRepository {
	List<Prices> findAll();
    Optional<Prices> getPriceByProductIdAndBrandIdAndDateApply(Long productId, Long brandId, Date date);
    Prices save(Prices prices);
}
