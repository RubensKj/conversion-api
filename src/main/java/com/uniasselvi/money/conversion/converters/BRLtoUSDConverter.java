package com.uniasselvi.money.conversion.converters;

import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class BRLtoUSDConverter implements CurrencyConverter {

    @Override
    public ConversionResponse convert(BigDecimal amount) {
        return new ConversionResponse(
                amount.divide(new BigDecimal("5.54"), 4, RoundingMode.HALF_UP)
        );
    }
}
