package com.deskly.desklycore.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deskly-core/health")
public class HealthController {

    private final Logger log = LoggerFactory.getLogger(HealthController.class);

    @GetMapping
    public String health(){
        log.info("Health endpoint triggered");
        return "Hello from deskly-core ";
    }

}
