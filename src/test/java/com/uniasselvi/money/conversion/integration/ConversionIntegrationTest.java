package com.uniasselvi.money.conversion.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConversionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should convert BRL to USD successfully")
    void shouldConvertBRLtoUSDSuccessfully() throws Exception {
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
    @DisplayName("Should convert BRL to EUR successfully")
    void shouldConvertBRLtoEURSuccessfully() throws Exception {
        // When & Then
        mockMvc.perform(get("/conversions")
                        .param("from", "BRL")
                        .param("to", "EUR")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conversion").value(15.625));
    }

    @Test
    @DisplayName("Should handle unsupported conversion")
    void shouldHandleUnsupportedConversion() throws Exception {
        // When & Then
        // The controller doesn't have an exception handler for ConversionException,
        // so it will result in a 500 Internal Server Error
        try {
            mockMvc.perform(get("/conversions")
                            .param("from", "USD")
                            .param("to", "BRL")
                            .param("amount", "100")
                            .contentType(MediaType.APPLICATION_JSON));
            fail("Expected ServletException with ConversionException cause");
        } catch (Exception e) {
            // Verify that the exception is a ServletException caused by a ConversionException
            assertTrue(e instanceof jakarta.servlet.ServletException);
            assertTrue(e.getCause() instanceof com.uniasselvi.money.conversion.exception.ConversionException);
            assertTrue(e.getCause().getMessage().contains("Conversion for [USD] to [BRL] is not supported"));
        }
    }

    @Test
    @DisplayName("Should handle case-insensitive currency codes")
    void shouldHandleCaseInsensitiveCurrencyCodes() throws Exception {
        // When & Then
        mockMvc.perform(get("/conversions")
                        .param("from", "brl")
                        .param("to", "usd")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conversion").value(18.0505));
    }

    @Test
    @DisplayName("Should handle invalid amount parameter")
    void shouldHandleInvalidAmountParameter() throws Exception {
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
        // When & Then
        mockMvc.perform(get("/conversions")
                        .param("from", "BRL")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
