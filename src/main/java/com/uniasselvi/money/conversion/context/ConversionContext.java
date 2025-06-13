package com.uniasselvi.money.conversion.context;

import java.math.BigDecimal;

public record ConversionContext(String fromCurrencyId, String toCurrencyId, BigDecimal amount) {
}
