package com.inditex.infrastructure.adapter.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inditex.domain.model.Prices;

@Repository
public interface PricesJpaCrudRepository extends JpaRepository<Prices, Long> {

    @Query(value = "select * from PRICES prices "
            + "where prices.product_id = :productId and prices.brand_id = :brandId "
            + "and :date BETWEEN prices.start_date and prices.end_date "
            + "order by prices.priority desc limit 1", nativeQuery = true)
    Optional<Prices> getPriceByProductIdAndBrandIdAndDateApply(@Param("productId") Long productId,
                                                               @Param("brandId") Long brandId,
                                                               @Param("date") Date date);
}
