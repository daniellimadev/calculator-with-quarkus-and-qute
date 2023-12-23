package com.github.daniellimadev.repositories;

import com.github.daniellimadev.entity.Result;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ResultRepository implements PanacheRepository<Result> {

    public Result create(Result result) {
        persistAndFlush(result);

        return result;
    }

    public List<Result> listAll() {
        return listAll(Sort.ascending("id"));
    }

}
