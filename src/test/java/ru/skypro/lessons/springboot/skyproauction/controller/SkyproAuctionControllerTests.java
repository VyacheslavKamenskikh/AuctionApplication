package ru.skypro.lessons.springboot.skyproauction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import ru.skypro.lessons.springboot.skyproauction.dto.Bidder;
import ru.skypro.lessons.springboot.skyproauction.dto.Status;
import ru.skypro.lessons.springboot.skyproauction.entity.Bid;
import ru.skypro.lessons.springboot.skyproauction.entity.Lot;
import ru.skypro.lessons.springboot.skyproauction.repository.BidRepository;
import ru.skypro.lessons.springboot.skyproauction.repository.LotRepository;
import ru.skypro.lessons.springboot.skyproauction.service.LotService;

import java.time.OffsetDateTime;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SkyproAuctionControllerTests {
    private static final String LOTS_URL = "/lot";
    private static final String STOP_URL = "/lot/{id}/stop";
    private static final String START_URL = "/lot/{id}/start";
    private static final String ID = "0";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BidRepository bidRepository;
    @Autowired
    LotRepository lotRepository;

    @Autowired
    LotService lotService;


    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void addLotTest(){
        Lot lot = new Lot();
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        String jsonLot =   objectMapper.writeValueAsString(lot);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    void addBidTest(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setId(Integer.parseInt(fId));
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        Bidder bidder = new Bidder();
        bidder.setName("test");
        String jsonLot =   objectMapper.writeValueAsString(lot);
        String jsonBid =   objectMapper.writeValueAsString(bidder);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/start",fId)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/bid",fId).contentType(MediaType.APPLICATION_JSON).content(jsonBid)).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @SneakyThrows
    @Test
    void AddLot_And_getFullLotTest(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setId(Integer.parseInt(fId));
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        String jsonLot =   objectMapper.writeValueAsString(lot);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(get(LOTS_URL)).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(fId));
    }

    @SneakyThrows
    @Test
    void FindLotByStatusAndPageTest(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setId(Integer.parseInt(fId));
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        Status status = Status.CREATED;
        String jsonStatus =   objectMapper.writeValueAsString(status);
        Integer page = 0;
        String jsonPage =objectMapper.writeValueAsString(page);
        String jsonLot =   objectMapper.writeValueAsString(lot);
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("status", String.valueOf(status));
        requestParams.add("page", String.valueOf(page));
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(get(LOTS_URL).params(requestParams)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }

    @SneakyThrows
    @Test
    void GetFirstBidder_Test(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setId(Integer.parseInt(fId));
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        Bidder bidder = new Bidder();
        bidder.setName("test");
        String jsonLot =   objectMapper.writeValueAsString(lot);
        String jsonBid =   objectMapper.writeValueAsString(bidder);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/start",fId)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/bid",fId).contentType(MediaType.APPLICATION_JSON).content(jsonBid)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(get(LOTS_URL+"/{id}/first",fId)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }

    @SneakyThrows
    @Test
    void GetCSVFile_Test(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setId(Integer.parseInt(fId));
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        Bidder bidder = new Bidder();
        bidder.setName("test");
        String jsonLot =   objectMapper.writeValueAsString(lot);
        String jsonBid =   objectMapper.writeValueAsString(bidder);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(get(LOTS_URL+"/export")).andExpect(status().isOk());
    }


    @SneakyThrows
    @Test
    void GetMostFrequentBidderTest(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setId(Integer.parseInt(fId));
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        Bidder bidder = new Bidder();
        bidder.setName("test");
        String jsonLot =   objectMapper.writeValueAsString(lot);
        String jsonBid =   objectMapper.writeValueAsString(bidder);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/start",fId)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/bid",fId).contentType(MediaType.APPLICATION_JSON).content(jsonBid)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(get(LOTS_URL+"/{id}/frequent",fId)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }


    @SneakyThrows
    @Test
    void startBiddingTest(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        String jsonLot =   objectMapper.writeValueAsString(lot);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/start",fId)).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @SneakyThrows
    @Test
    void stopBiddingTest(){
        final String fId = "1";
        Lot lot = new Lot();
        lot.setTitle("test");
        lot.setDescribtion("test");
        lot.setStatus(Status.CREATED);
        lot.setBidPrice(12);
        lot.setStartPrice(20);
        String jsonLot =   objectMapper.writeValueAsString(lot);
        mockMvc.perform(post(LOTS_URL).contentType(MediaType.APPLICATION_JSON).content(jsonLot)).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(post(LOTS_URL+"/{id}/stop",fId)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    private String getLotAsJsonString(String title){
        Lot lot = new Lot();
        lot.setTitle(title);
        return objectMapper.writeValueAsString(lot);
    }
}
