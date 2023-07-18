package ru.skypro.lessons.springboot.skyproauction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.lessons.springboot.skyproauction.service.LotService;

@SpringBootTest
class SkyproAuctionApplicationTests {

    @Autowired
    private LotService lotService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(lotService);
    }

}
