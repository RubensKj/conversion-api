package com.uniasselvi.money.conversion.converters;

import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BRLtoJPYConverter implements CurrencyConverter {

    @Override
    public ConversionResponse convert(BigDecimal amount) {
        return new ConversionResponse(
                amount.divide(new BigDecimal("0.037"), 4, RoundingMode.HALF_UP)
        );
    }
}