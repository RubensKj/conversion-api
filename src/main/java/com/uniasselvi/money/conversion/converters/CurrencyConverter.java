package com.uniasselvi.money.conversion.converters;

import com.uniasselvi.money.conversion.conversions.ConversionResponse;

import java.math.BigDecimal;

public interface CurrencyConverter {

    ConversionResponse convert(BigDecimal amount);
}
