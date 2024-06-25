package com.project.springbatch.App;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Service service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/info")
    public ResponseEntity<Entity> signUp(){
        logger.info(" ====== /api/v1/signup [{}.signup()] start ======", getClass().getSimpleName());

        Entity entity = Entity.builder()
                .name("김무건")
                .build();

        logger.info(" /api/v1/signup [{}.signup()]  signup: {}", getClass().getSimpleName(), entity.getName());

        Entity result = service.save(entity);

        logger.info(" ====== /api/v1/signup [{}.signup()] end ======", getClass().getSimpleName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/error")
    public ResponseEntity<Entity> error(){
        logger.error(" ====== /api/v1/signup [{}.signup()] start ======", getClass().getSimpleName());

        Entity entity = Entity.builder()
                .name("김무건")
                .build();

        logger.error(" /api/v1/signup [{}.signup()]  signup: {}", getClass().getSimpleName(), entity.getName());

        Entity result = service.save(entity);

        logger.error(" ====== /api/v1/signup [{}.signup()] end ======", getClass().getSimpleName());
        return ResponseEntity.ok(result);
    }
}
