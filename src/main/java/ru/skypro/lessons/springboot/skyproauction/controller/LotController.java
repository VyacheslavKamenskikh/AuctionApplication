package ru.skypro.lessons.springboot.skyproauction.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.skyproauction.dto.*;
import ru.skypro.lessons.springboot.skyproauction.service.LotService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lot")
public class LotController {
    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping("/{id}/first")
    public Bid getFirstBidder(@PathVariable int id){
        return lotService.getFirstBidder(id);
    }

    @GetMapping("/{id}/frequent")
    public Bid getMostFrequentBidder(@PathVariable int id){
        return lotService.getMostFrequentBidder(id);
    }

    @GetMapping("/{id}")
    public FullLot getFullLot(@PathVariable int id){
        return lotService.getFullLot(id);
    }

    @PostMapping("/{id}/start")
    public void startBidding(@PathVariable int id){
        lotService.startBidding(id);
    }

    @PostMapping("/{id}/bid")
    public void createBid(@PathVariable int id, @RequestBody @Valid Bidder bidder){
        lotService.createBid(id,bidder);
    }

    @PostMapping("/{id}/stop")
    public void stopBidding(@PathVariable int id){
        lotService.stopBidding(id);
    }

    @PostMapping
    public Lot createLot(@RequestBody @Valid CreateLot createLot){
        return lotService.createLot(createLot);
    }

    @GetMapping
    public List<Lot> findLots(@RequestParam(value = "status", required = false) Status status,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page){
        return lotService.findLots(status, page);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> getCSVFile() throws IOException {
        byte[] result = lotService.getCSVFile();
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE,"text/csv").body(result);
    }
}
