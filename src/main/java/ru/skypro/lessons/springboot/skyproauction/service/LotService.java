package ru.skypro.lessons.springboot.skyproauction.service;

import jakarta.annotation.Nullable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.skyproauction.dto.*;
import ru.skypro.lessons.springboot.skyproauction.entity.Lot;
import ru.skypro.lessons.springboot.skyproauction.exception.LotNotFoundException;
import ru.skypro.lessons.springboot.skyproauction.exception.LotNotStarted;
import ru.skypro.lessons.springboot.skyproauction.mapper.LotMapper;
import ru.skypro.lessons.springboot.skyproauction.repository.BidRepository;
import ru.skypro.lessons.springboot.skyproauction.repository.LotRepository;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotService {
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final LotMapper lotMapper;
    public LotService(LotRepository lotRepository, BidRepository bidRepository, LotMapper lotMapper) {
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
        this.lotMapper = lotMapper;
    }

    public Bid getMostFrequentBidder(int id) {
        return bidRepository.findTheMostFrequentBidder(id).orElseThrow(LotNotFoundException::new);
    }

    public Bid getFirstBidder(int id) {
        return bidRepository.findFirstByLot_IdOrderByDateTimeAsc(id).map(lotMapper::toDto).orElseThrow(LotNotFoundException::new);
    }

    public FullLot getFullLot(int id) {
        return lotRepository.getFullLot(id).orElseThrow(LotNotFoundException::new);
    }

    public void startBidding(int id) {
        changeLotStatus(id, Status.STARTED);
    }

    public void createBid(int id, Bidder bidder) {
        Lot lot = lotRepository.findById(id).orElseThrow(LotNotFoundException::new);
        if(lot.getStatus()== Status.CREATED || lot.getStatus()== Status.STOPPED){
            throw new LotNotStarted();
        }
        else {
          bidRepository.save(new ru.skypro.lessons.springboot.skyproauction.entity.Bid(bidder.getName(), lot));
        }
    }

    public void stopBidding(int id) {
        changeLotStatus(id, Status.STOPPED);
    }

    private void changeLotStatus(int id, Status status){
        Lot lot = lotRepository.findById(id).orElseThrow(LotNotFoundException::new);
        lot.setStatus(status);
        lotRepository.save(lot);
    }

    public ru.skypro.lessons.springboot.skyproauction.dto.Lot createLot(CreateLot createLot) {
        return lotMapper.toDto(
                lotRepository.save(lotMapper.toEntity(createLot))
        );
    }

    public List<ru.skypro.lessons.springboot.skyproauction.dto.Lot> findLots(@Nullable Status status, int page) {
        Pageable pageable = PageRequest.of(page,100);
        return Optional.ofNullable(status).map(st-> lotRepository.findAllByStatus(st,pageable)).orElseGet(()-> lotRepository.findAll(pageable))
                .stream().map(lotMapper::toDto).collect(Collectors.toList());
    }

    public byte[] getCSVFile() throws IOException {
        List<Lot> lots = lotRepository.findAll();
        StringWriter stringWriter = new StringWriter();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("id","title","status","lastBidder","currentPrice")
                .build();

        try (CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat)) {
            lots.forEach(lot -> {
                try {
                    csvPrinter.printRecord(lot.getId(),lot.getTitle(),lot.getStatus(),lot.getBidPrice(),lot.getStartPrice()+ lot.getBidPrice());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (IOException e){
            throw new RuntimeException(e);

        }
        return new byte[0];
    }
}
