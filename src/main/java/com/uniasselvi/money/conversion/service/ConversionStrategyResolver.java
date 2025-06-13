package com.uniasselvi.money.conversion.service;

import com.uniasselvi.money.conversion.converters.BRLtoCADConverter;
import com.uniasselvi.money.conversion.converters.BRLtoEURConverter;
import com.uniasselvi.money.conversion.converters.BRLtoGBPConverter;
import com.uniasselvi.money.conversion.converters.BRLtoJPYConverter;
import com.uniasselvi.money.conversion.converters.BRLtoUSDConverter;
import com.uniasselvi.money.conversion.converters.CurrencyConverter;
import com.uniasselvi.money.conversion.exception.ConversionException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConversionStrategyResolver {

    private final Map<String, CurrencyConverter> converters = new HashMap<>();

    public ConversionStrategyResolver(
            BRLtoUSDConverter brlToUSDConverter, 
            BRLtoEURConverter brlToEURConverter,
            BRLtoGBPConverter brlToGBPConverter,
            BRLtoJPYConverter brlToJPYConverter,
            BRLtoCADConverter brlToCADConverter) {
        // Register all available converters
        registerConverter("BRL", "USD", brlToUSDConverter);
        registerConverter("BRL", "EUR", brlToEURConverter);
        registerConverter("BRL", "GBP", brlToGBPConverter);
        registerConverter("BRL", "JPY", brlToJPYConverter);
        registerConverter("BRL", "CAD", brlToCADConverter);
    }

    /**
     * Registers a converter for a specific currency pair
     * @param from source currency code
     * @param to target currency code
     * @param converter the converter implementation
     */
    private void registerConverter(String from, String to, CurrencyConverter converter) {
        String key = createKey(from, to);
        converters.put(key, converter);
    }

    /**
     * Creates a standardized key for the converters map
     * @param from source currency code
     * @param to target currency code
     * @return standardized key
     */
    private String createKey(String from, String to) {
        return from.toUpperCase() + "_TO_" + to.toUpperCase();
    }

    /**
     * Resolves the appropriate converter for the given currency pair
     * @param from source currency code
     * @param to target currency code
     * @return the appropriate converter
     * @throws ConversionException if no converter is found for the given currency pair
     */
    public CurrencyConverter resolve(String from, String to) {
        String key = createKey(from, to);
        CurrencyConverter converter = converters.get(key);

        if (converter == null) {
            throw new ConversionException("Conversion for [" + from + "] to [" + to + "] is not supported");
        }

        return converter;
    }
}
