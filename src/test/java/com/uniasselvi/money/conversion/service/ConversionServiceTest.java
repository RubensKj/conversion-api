package com.uniasselvi.money.conversion.service;

import com.uniasselvi.money.conversion.context.ConversionContext;
import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import com.uniasselvi.money.conversion.exception.ConversionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {

    @InjectMocks
    private ConversionService conversionService;

    @Test
    @DisplayName("Should convert BRL to USD correctly")
    void shouldConvertBRLtoUSDCorrectly() {
        // Given
        ConversionContext context = new ConversionContext("BRL", "USD", new BigDecimal("100"));
        BigDecimal expectedAmount = new BigDecimal("100").divide(new BigDecimal("5.54"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = conversionService.convert(context);

        // Then
        assertEquals(expectedAmount, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should convert BRL to EUR correctly")
    void shouldConvertBRLtoEURCorrectly() {
        // Given
        ConversionContext context = new ConversionContext("BRL", "EUR", new BigDecimal("100"));
        BigDecimal expectedAmount = new BigDecimal("100").divide(new BigDecimal("6.40"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response = conversionService.convert(context);

        // Then
        assertEquals(expectedAmount, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should throw exception for unsupported conversion")
    void shouldThrowExceptionForUnsupportedConversion() {
        // Given
        ConversionContext context = new ConversionContext("USD", "BRL", new BigDecimal("100"));

        // When & Then
        ConversionException exception = assertThrows(
                ConversionException.class,
                () -> conversionService.convert(context),
                "Should throw ConversionException for unsupported conversion"
        );

        assertTrue(exception.getMessage().contains("USD") && exception.getMessage().contains("BRL"),
                "Exception message should contain the unsupported currency codes");
    }

    @Test
    @DisplayName("Should handle case-insensitive currency codes")
    void shouldHandleCaseInsensitiveCurrencyCodes() {
        // Given
        ConversionContext context1 = new ConversionContext("brl", "usd", new BigDecimal("100"));
        ConversionContext context2 = new ConversionContext("Brl", "Eur", new BigDecimal("100"));
        
        BigDecimal expectedUsdAmount = new BigDecimal("100").divide(new BigDecimal("5.54"), 4, RoundingMode.HALF_UP);
        BigDecimal expectedEurAmount = new BigDecimal("100").divide(new BigDecimal("6.40"), 4, RoundingMode.HALF_UP);

        // When
        ConversionResponse response1 = conversionService.convert(context1);
        ConversionResponse response2 = conversionService.convert(context2);

        // Then
        assertEquals(expectedUsdAmount, response1.conversion(), "Should convert lowercase BRL to USD correctly");
        assertEquals(expectedEurAmount, response2.conversion(), "Should convert mixed case BRL to EUR correctly");
    }
}