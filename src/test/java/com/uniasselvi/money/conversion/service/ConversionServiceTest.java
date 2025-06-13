package com.uniasselvi.money.conversion.service;

import com.uniasselvi.money.conversion.context.ConversionContext;
import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import com.uniasselvi.money.conversion.converters.BRLtoCADConverter;
import com.uniasselvi.money.conversion.converters.BRLtoEURConverter;
import com.uniasselvi.money.conversion.converters.BRLtoGBPConverter;
import com.uniasselvi.money.conversion.converters.BRLtoJPYConverter;
import com.uniasselvi.money.conversion.converters.BRLtoUSDConverter;
import com.uniasselvi.money.conversion.exception.ConversionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {

    @Mock
    private ConversionStrategyResolver strategyResolver;

    @Mock
    private BRLtoUSDConverter brlToUSDConverter;

    @Mock
    private BRLtoEURConverter brlToEURConverter;

    @Mock
    private BRLtoGBPConverter brlToGBPConverter;

    @Mock
    private BRLtoJPYConverter brlToJPYConverter;

    @Mock
    private BRLtoCADConverter brlToCADConverter;

    @InjectMocks
    private ConversionService conversionService;

    // No setup needed here, we'll set up the mocks in each test method

    @Test
    @DisplayName("Should convert BRL to USD correctly")
    void shouldConvertBRLtoUSDCorrectly() {
        // Given
        ConversionContext context = new ConversionContext("BRL", "USD", new BigDecimal("100"));
        BigDecimal expectedAmount = new BigDecimal("100").divide(new BigDecimal("5.54"), 4, RoundingMode.HALF_UP);

        when(strategyResolver.resolve("BRL", "USD")).thenReturn(brlToUSDConverter);
        when(brlToUSDConverter.convert(new BigDecimal("100"))).thenReturn(new ConversionResponse(expectedAmount));

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

        when(strategyResolver.resolve("BRL", "EUR")).thenReturn(brlToEURConverter);
        when(brlToEURConverter.convert(new BigDecimal("100"))).thenReturn(new ConversionResponse(expectedAmount));

        // When
        ConversionResponse response = conversionService.convert(context);

        // Then
        assertEquals(expectedAmount, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should convert BRL to GBP correctly")
    void shouldConvertBRLtoGBPCorrectly() {
        // Given
        ConversionContext context = new ConversionContext("BRL", "GBP", new BigDecimal("100"));
        BigDecimal expectedAmount = new BigDecimal("100").divide(new BigDecimal("7.20"), 4, RoundingMode.HALF_UP);

        when(strategyResolver.resolve("BRL", "GBP")).thenReturn(brlToGBPConverter);
        when(brlToGBPConverter.convert(new BigDecimal("100"))).thenReturn(new ConversionResponse(expectedAmount));

        // When
        ConversionResponse response = conversionService.convert(context);

        // Then
        assertEquals(expectedAmount, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should convert BRL to JPY correctly")
    void shouldConvertBRLtoJPYCorrectly() {
        // Given
        ConversionContext context = new ConversionContext("BRL", "JPY", new BigDecimal("100"));
        BigDecimal expectedAmount = new BigDecimal("100").divide(new BigDecimal("0.037"), 4, RoundingMode.HALF_UP);

        when(strategyResolver.resolve("BRL", "JPY")).thenReturn(brlToJPYConverter);
        when(brlToJPYConverter.convert(new BigDecimal("100"))).thenReturn(new ConversionResponse(expectedAmount));

        // When
        ConversionResponse response = conversionService.convert(context);

        // Then
        assertEquals(expectedAmount, response.conversion(), "The converted amount should match the expected value");
    }

    @Test
    @DisplayName("Should convert BRL to CAD correctly")
    void shouldConvertBRLtoCADCorrectly() {
        // Given
        ConversionContext context = new ConversionContext("BRL", "CAD", new BigDecimal("100"));
        BigDecimal expectedAmount = new BigDecimal("100").divide(new BigDecimal("4.10"), 4, RoundingMode.HALF_UP);

        when(strategyResolver.resolve("BRL", "CAD")).thenReturn(brlToCADConverter);
        when(brlToCADConverter.convert(new BigDecimal("100"))).thenReturn(new ConversionResponse(expectedAmount));

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

        when(strategyResolver.resolve("USD", "BRL")).thenThrow(new ConversionException("Conversion for [USD] to [BRL] is not supported"));

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

        when(strategyResolver.resolve("brl", "usd")).thenReturn(brlToUSDConverter);
        when(strategyResolver.resolve("Brl", "Eur")).thenReturn(brlToEURConverter);
        when(brlToUSDConverter.convert(new BigDecimal("100"))).thenReturn(new ConversionResponse(expectedUsdAmount));
        when(brlToEURConverter.convert(new BigDecimal("100"))).thenReturn(new ConversionResponse(expectedEurAmount));

        // When
        ConversionResponse response1 = conversionService.convert(context1);
        ConversionResponse response2 = conversionService.convert(context2);

        // Then
        assertEquals(expectedUsdAmount, response1.conversion(), "Should convert lowercase BRL to USD correctly");
        assertEquals(expectedEurAmount, response2.conversion(), "Should convert mixed case BRL to EUR correctly");
    }
}
