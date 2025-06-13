package com.uniasselvi.money.conversion.converters;

import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BRLtoCADConverterTest {

    private final BRLtoCADConverter converter = new BRLtoCADConverter();

    @Test
    @DisplayName("Should convert BRL to CAD correctly")
    void shouldConvertBRLtoCADCorrectly() {
        // Given
        BigDecimal amountInBRL = new BigDecimal("100");
        BigDecimal expectedAmountInCAD = amountInBRL.divide(new BigDecimal("4.10"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInCAD, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should handle zero amount conversion")
    void shouldHandleZeroAmountConversion() {
        // Given
        BigDecimal amountInBRL = BigDecimal.ZERO;
        BigDecimal expectedAmountInCAD = new BigDecimal("0.0000");

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInCAD, response.conversion(), "Zero BRL should convert to zero CAD");
    }

    @Test
    @DisplayName("Should handle very large amount conversion")
    void shouldHandleVeryLargeAmountConversion() {
        // Given
        BigDecimal amountInBRL = new BigDecimal("1000000");
        BigDecimal expectedAmountInCAD = amountInBRL.divide(new BigDecimal("4.10"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = converter.convert(amountInBRL);

        // Then
        assertEquals(expectedAmountInCAD, response.conversion(), "Large BRL amounts should be converted correctly");
    }
}