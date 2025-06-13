package com.uniasselvi.money.conversion.service;

import com.uniasselvi.money.conversion.context.ConversionContext;
import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import com.uniasselvi.money.conversion.converters.CurrencyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    private static final Logger log = LoggerFactory.getLogger(ConversionService.class);

    private final ConversionStrategyResolver strategyResolver;

    public ConversionService(ConversionStrategyResolver strategyResolver) {
        this.strategyResolver = strategyResolver;
    }

    public ConversionResponse convert(ConversionContext context) {
        CurrencyConverter resolve = strategyResolver.resolve(context.fromCurrencyId(), context.toCurrencyId());

        log.info("Converting from {} to {} - Amount: {}", context.fromCurrencyId(), context.toCurrencyId(), context.amount());

        return resolve.convert(context.amount());
    }
}
