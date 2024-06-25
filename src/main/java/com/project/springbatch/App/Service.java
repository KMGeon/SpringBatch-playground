package com.project.springbatch.App;

import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
public class Service {
    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    @Transactional
    public Entity save(Entity entity){
        return repository.save(entity);
    }
}
