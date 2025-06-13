package com.uniasselvi.money.conversion.service;

import com.uniasselvi.money.conversion.context.ConversionContext;
import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import com.uniasselvi.money.conversion.exception.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ConversionService {

    private static final Logger log = LoggerFactory.getLogger(ConversionService.class);

    public ConversionResponse convert(ConversionContext context) {
        String fromCurrencyId = context.fromCurrencyId();
        String toCurrencyId = context.toCurrencyId();
        BigDecimal amount = context.amount();

        log.info("Converting from {} to {} - Amount: {}", context.fromCurrencyId(), context.toCurrencyId(), context.amount());

        if (fromCurrencyId.equalsIgnoreCase("BRL") && toCurrencyId.equalsIgnoreCase("USD")) {
            return new ConversionResponse(
                    amount.divide(new BigDecimal("5.54"), 4, RoundingMode.HALF_UP)
            );
        }

        if (fromCurrencyId.equalsIgnoreCase("BRL") && toCurrencyId.equalsIgnoreCase("EUR")) {
            return new ConversionResponse(
                    amount.divide(new BigDecimal("6.40"), 4, RoundingMode.HALF_UP)
            );
        }

        throw new ConversionException("Conversion for [" + fromCurrencyId + "] to [" + toCurrencyId + "] is not supported");
    }
}
