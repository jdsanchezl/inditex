package com.inditex.infrastructure.adapter.repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inditex.domain.model.Prices;
import com.inditex.domain.repository.PricesRepository;

@Repository
public class PricesJpaRepository implements PricesRepository {

	private final PricesJpaCrudRepository pricesJpaCrudRepository;

    @Autowired
    public PricesJpaRepository(PricesJpaCrudRepository pricesJpaCrudRepository) {
        this.pricesJpaCrudRepository = pricesJpaCrudRepository;
    }

    @Override
    public List<Prices> findAll() {
        return pricesJpaCrudRepository.findAll();
    }

    @Override
    public Optional<Prices> getPriceByProductIdAndBrandIdAndDateApply(Long productId, Long brandId, Date date) {
        return pricesJpaCrudRepository.getPriceByProductIdAndBrandIdAndDateApply(productId, brandId, date);
    }

    @Override
    public Prices save(Prices prices) {
        return pricesJpaCrudRepository.save(prices);
    }
}