package com.uniasselvi.money.conversion.converters;

import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BRLtoUSDConverterTest {

    private final BRLtoUSDConverter converter = new BRLtoUSDConverter();

    @Test
    @DisplayName("Should convert BRL to USD correctly")
    void shouldConvertBRLtoUSDCorrectly() {
        // Given
        BigDecimal amountInBRL = new BigDecimal("100");
        BigDecimal expectedAmountInUSD = amountInBRL.divide(new BigDecimal("5.54"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInUSD, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should handle zero amount conversion")
    void shouldHandleZeroAmountConversion() {
        // Given
        BigDecimal amountInBRL = BigDecimal.ZERO;
        BigDecimal expectedAmountInUSD = new BigDecimal("0.0000");

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInUSD, response.conversion(), "Zero BRL should convert to zero USD");
    }

    @Test
    @DisplayName("Should handle very large amount conversion")
    void shouldHandleVeryLargeAmountConversion() {
        // Given
        BigDecimal amountInBRL = new BigDecimal("1000000");
        BigDecimal expectedAmountInUSD = amountInBRL.divide(new BigDecimal("5.54"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInUSD, response.conversion(), "Large BRL amounts should be converted correctly");
    }
}
