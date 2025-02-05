package com.inditex.application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inditex.application.service.PricesService;
import com.inditex.domain.model.Prices;
import com.inditex.domain.repository.PricesRepository;

@Service
public class PricesServiceImpl implements PricesService {

	private final PricesRepository pricesRepository;

    public PricesServiceImpl(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

	@Override
	public List<Prices> getPricesAll() {
		List<Prices> prices = new ArrayList<Prices>();
		pricesRepository.findAll().forEach(prices::add);
		return prices;
	}

	@Override
	public Optional<Prices> getPricesConditional(String apply, Long productId, Long brandId) throws ParseException {
		Date applyDate = (new SimpleDateFormat("yyy-MM-dd-hh.mm.ss")).parse(apply);
		return pricesRepository.getPriceByProductIdAndBrandIdAndDateApply(productId, brandId, applyDate);
	}

	@Override
	public Prices savePrices(Prices prices) {
		return pricesRepository.save(prices);
	}
}
