package com.github.daniellimadev.resource;

import com.github.daniellimadev.dto.ExternalTextDTO;
import com.github.daniellimadev.dto.TemplateDataDTO;
import com.github.daniellimadev.enums.EnumUserOption;
import com.github.daniellimadev.services.CalculatorSerivce;
import com.github.daniellimadev.services.ResultService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Path("/calculator")
@Consumes(MediaType.TEXT_HTML)
@Produces(MediaType.TEXT_HTML)
public class CalculatorResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance page(TemplateDataDTO dataDTO);

        public static native TemplateInstance calculator(TemplateDataDTO dataDTO);
    }

    @Inject
    CalculatorSerivce service;

    @Inject
    @RestClient
    RestHttpClient restClient;

    @Inject
    ResultService resultService;

    TemplateDataDTO dataDTO = new TemplateDataDTO();

    @GET
    public TemplateInstance get(@QueryParam("name") String name) {
        ExternalTextDTO externalTextDTO = restClient.fetchExternalData();
        dataDTO.setExternalText(externalTextDTO.getText());

        List<String> arithmeticOperations = List.of(EnumUserOption.values()).stream()
                .map(e -> e.getValue()).collect(Collectors.toList());
        dataDTO.setArithmeticOperations(arithmeticOperations);

        return Templates.page(dataDTO);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public TemplateInstance calculate(@FormParam("firstNumber") String firstNumber,
            @FormParam("secondNumber") String secondNumber, @FormParam("operation") String operation) {

        BigDecimal valueOne = BigDecimal.valueOf(Double.parseDouble(firstNumber));
        BigDecimal valueTwo = BigDecimal.valueOf(Double.parseDouble(secondNumber));
        EnumUserOption parsedOption = EnumUserOption.parseByValue(operation);

        if (valueTwo.equals(BigDecimal.ZERO)) {
            throw new RuntimeException("Error! Invalid second number");
        }
        
        if (parsedOption == null) {
            throw new RuntimeException("Error! Invalid selected option!");
        }

        BigDecimal resultValue = service.handleCalculate(parsedOption, valueOne, valueTwo);

        resultService.saveResult(parsedOption, secondNumber, operation, resultValue);

        dataDTO.setResults(resultService.getAll());
        dataDTO.setResult(resultValue);

        return Templates.calculator(dataDTO);
    }

}
