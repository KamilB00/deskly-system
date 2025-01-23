package com.deskly.desklylocation.location;

import com.deskly.desklylocation.TestDbConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({TestDbConfiguration.class})

class CreatingLocationTest {

    @Autowired
    LocationFacade locationFacade;

    void createLocationTest() {

    }
}