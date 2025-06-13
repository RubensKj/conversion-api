package com.uniasselvi.money.conversion.converters;

import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BRLtoGBPConverterTest {

    private final BRLtoGBPConverter converter = new BRLtoGBPConverter();

    @Test
    @DisplayName("Should convert BRL to GBP correctly")
    void shouldConvertBRLtoGBPCorrectly() {
        // Given
        BigDecimal amountInBRL = new BigDecimal("100");
        BigDecimal expectedAmountInGBP = amountInBRL.divide(new BigDecimal("7.20"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInGBP, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should handle zero amount conversion")
    void shouldHandleZeroAmountConversion() {
        // Given
        BigDecimal amountInBRL = BigDecimal.ZERO;
        BigDecimal expectedAmountInGBP = new BigDecimal("0.0000");

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInGBP, response.conversion(), "Zero BRL should convert to zero GBP");
    }

    @Test
    @DisplayName("Should handle very large amount conversion")
    void shouldHandleVeryLargeAmountConversion() {
        // Given
        BigDecimal amountInBRL = new BigDecimal("1000000");
        BigDecimal expectedAmountInGBP = amountInBRL.divide(new BigDecimal("7.20"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInGBP, response.conversion(), "Large BRL amounts should be converted correctly");
    }
}