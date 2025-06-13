package com.uniasselvi.money.conversion.controller;

import com.uniasselvi.money.conversion.context.ConversionContext;
import com.uniasselvi.money.conversion.conversions.ConversionResponse;
import com.uniasselvi.money.conversion.dto.ConversionDto;
import com.uniasselvi.money.conversion.service.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/conversions")
public class ConversionController {

    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping
    public ConversionDto conversion(
            @RequestParam("from") String fromCurrencyId,
            @RequestParam("to") String toCurrencyId,
            @RequestParam("amount") BigDecimal amount
    ) {
        ConversionContext context = new ConversionContext(fromCurrencyId, toCurrencyId, amount);

        ConversionResponse response = conversionService.convert(context);

        return ConversionDto.of(response);
    }
}
