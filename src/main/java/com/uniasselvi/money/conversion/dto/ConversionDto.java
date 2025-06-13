package com.uniasselvi.money.conversion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uniasselvi.money.conversion.conversions.ConversionResponse;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConversionDto(BigDecimal conversion,
                            String formattedConversion,
                            String fromCurrencySign,
                            String toCurrencySign) {

    public static ConversionDto of(ConversionResponse response) {
        // TODO: Implement other fields later.
        return new ConversionDto(
                response.conversion(),
                null,
                null,
                null
        );
    }
}
