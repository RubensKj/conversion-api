package com.uniasselvi.money.conversion.service;

import com.uniasselvi.money.conversion.converters.BRLtoEURConverter;
import com.uniasselvi.money.conversion.converters.BRLtoUSDConverter;
import com.uniasselvi.money.conversion.converters.CurrencyConverter;
import com.uniasselvi.money.conversion.exception.ConversionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConversionStrategyResolverTest {

    @Mock
    private BRLtoUSDConverter brlToUSDConverter;

    @Mock
    private BRLtoEURConverter brlToEURConverter;

    private ConversionStrategyResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new ConversionStrategyResolver(brlToUSDConverter, brlToEURConverter);
    }

    @Test
    @DisplayName("Should resolve BRL to USD converter")
    void shouldResolveBRLtoUSDConverter() {
        // When
        CurrencyConverter converter = resolver.resolve("BRL", "USD");

        // Then
        assertSame(brlToUSDConverter, converter, "Should return the BRL to USD converter");
    }

    @Test
    @DisplayName("Should resolve BRL to EUR converter")
    void shouldResolveBRLtoEURConverter() {
        // When
        CurrencyConverter converter = resolver.resolve("BRL", "EUR");

        // Then
        assertSame(brlToEURConverter, converter, "Should return the BRL to EUR converter");
    }

    @Test
    @DisplayName("Should handle case-insensitive currency codes")
    void shouldHandleCaseInsensitiveCurrencyCodes() {
        // When
        CurrencyConverter converter1 = resolver.resolve("brl", "usd");
        CurrencyConverter converter2 = resolver.resolve("Brl", "Eur");

        // Then
        assertSame(brlToUSDConverter, converter1, "Should return the BRL to USD converter for lowercase codes");
        assertSame(brlToEURConverter, converter2, "Should return the BRL to EUR converter for mixed case codes");
    }

    @Test
    @DisplayName("Should throw exception for unsupported conversion")
    void shouldThrowExceptionForUnsupportedConversion() {
        // When & Then
        ConversionException exception = assertThrows(
                ConversionException.class,
                () -> resolver.resolve("USD", "BRL"),
                "Should throw ConversionException for unsupported conversion"
        );

        assertTrue(exception.getMessage().contains("USD") && exception.getMessage().contains("BRL"),
                "Exception message should contain the unsupported currency codes");
    }
}