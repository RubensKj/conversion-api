package com.uniasselvi.money.conversion.controller;

import com.uniasselvi.money.conversion.context.ConversionContext;
import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import com.uniasselvi.money.conversion.exception.ConversionException;
import com.uniasselvi.money.conversion.service.ConversionService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ConversionControllerTest {

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private ConversionController conversionController;

    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return converted amount when conversion is successful")
    void shouldReturnConvertedAmountWhenConversionIsSuccessful() throws Exception {
        // Given
        BigDecimal convertedAmount = new BigDecimal("18.0505");
        when(conversionService.convert(any(ConversionContext.class)))
                .thenReturn(new ConversionResponse(convertedAmount));

        mockMvc = MockMvcBuilders.standaloneSetup(conversionController).build();

        // When & Then
        mockMvc.perform(get("/conversions")
                        .param("from", "BRL")
                        .param("to", "USD")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conversion").value(18.0505));
    }

    @Test
    @DisplayName("Should handle conversion exception")
    void shouldHandleConversionException() throws Exception {
        // Given
        when(conversionService.convert(any(ConversionContext.class)))
                .thenThrow(new ConversionException("Conversion not supported"));

        mockMvc = MockMvcBuilders.standaloneSetup(conversionController)
                .build();

        // When & Then
        // The controller doesn't have an exception handler for ConversionException,
        // so it will result in a 500 Internal Server Error
        try {
            mockMvc.perform(get("/conversions")
                            .param("from", "USD")
                            .param("to", "BRL")
                            .param("amount", "100")
                            .contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            // Verify that the exception is a ServletException caused by a ConversionException
            assertInstanceOf(ServletException.class, e);
            assertInstanceOf(ConversionException.class, e.getCause());
            assertTrue(e.getCause().getMessage().contains("Conversion not supported"));
            return;
        }

        // If we get here, the test should fail because we expected an exception
        fail("Expected ServletException with ConversionException cause");
    }

    @Test
    @DisplayName("Should handle invalid amount parameter")
    void shouldHandleInvalidAmountParameter() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(conversionController).build();

        // When & Then
        mockMvc.perform(get("/conversions")
                        .param("from", "BRL")
                        .param("to", "USD")
                        .param("amount", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle missing parameters")
    void shouldHandleMissingParameters() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(conversionController).build();

        // When & Then
        mockMvc.perform(get("/conversions")
                        .param("from", "BRL")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
