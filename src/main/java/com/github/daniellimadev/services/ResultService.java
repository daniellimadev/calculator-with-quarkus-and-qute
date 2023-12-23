package com.github.daniellimadev.services;

import com.github.daniellimadev.entity.Result;
import com.github.daniellimadev.enums.EnumUserOption;
import com.github.daniellimadev.repositories.ResultRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ResultService {

    @Inject
    ResultRepository repository;

    @Transactional
    public void saveResult(EnumUserOption option, String firstValue, String secondValue, BigDecimal resultValue) {
        Result result = new Result();
        String operationText = option.getValue().concat(" - First number: ").concat(firstValue)
                .concat(" - Second value").concat(secondValue);

        result.setValue(resultValue);
        result.setOperation(operationText);

        repository.create(result);
    }

    public List<Result> getAll() {
        return repository.listAll();
    }

}
