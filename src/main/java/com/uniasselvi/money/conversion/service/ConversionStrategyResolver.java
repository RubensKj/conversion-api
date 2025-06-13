package com.uniasselvi.money.conversion.service;

import com.uniasselvi.money.conversion.converters.BRLtoEURConverter;
import com.uniasselvi.money.conversion.converters.BRLtoUSDConverter;
import com.uniasselvi.money.conversion.converters.CurrencyConverter;
import com.uniasselvi.money.conversion.exception.ConversionException;
import org.springframework.stereotype.Service;

@Service
public class ConversionStrategyResolver {

    private final BRLtoUSDConverter brlToUSDConverter;
    private final BRLtoEURConverter brlToEURConverter;

    public ConversionStrategyResolver(BRLtoUSDConverter brlToUSDConverter, BRLtoEURConverter brlToEURConverter) {
        this.brlToUSDConverter = brlToUSDConverter;
        this.brlToEURConverter = brlToEURConverter;
    }

    public CurrencyConverter resolve(String from, String to) {
        if (from.equalsIgnoreCase("BRL") && to.equalsIgnoreCase("USD")) {
            return brlToUSDConverter;
        }

        if (from.equalsIgnoreCase("BRL") && to.equalsIgnoreCase("EUR")) {
            return brlToEURConverter;
        }

        throw new ConversionException("Conversion for [" + from + "] to [" + to + "] is not supported");
    }
}
