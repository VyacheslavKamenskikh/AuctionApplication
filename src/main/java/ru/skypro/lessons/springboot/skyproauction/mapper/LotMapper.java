package ru.skypro.lessons.springboot.skyproauction.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.skyproauction.dto.Bid;
import ru.skypro.lessons.springboot.skyproauction.dto.CreateLot;
import ru.skypro.lessons.springboot.skyproauction.dto.Status;
import ru.skypro.lessons.springboot.skyproauction.entity.Lot;

@Component
public class LotMapper {
    public Bid toDto(ru.skypro.lessons.springboot.skyproauction.entity.Bid bid){
        Bid bidDto = new Bid();
        bidDto.setBidDate(bid.getDateTime());
        bidDto.setBidderName(bidDto.getBidderName());
        return bidDto;
    }

    public Lot toEntity(CreateLot createLot){
        Lot lot = new Lot();
        lot.setStatus(Status.CREATED);
        lot.setTitle(createLot.getTitle());
        lot.setDescribtion(createLot.getDescribtion());
        lot.setStartPrice(createLot.getStartPrice());
        lot.setBidPrice(createLot.getBidPrice());
        return lot;
    }

    public ru.skypro.lessons.springboot.skyproauction.dto.Lot toDto(Lot lot){
        ru.skypro.lessons.springboot.skyproauction.dto.Lot lotDto = new ru.skypro.lessons.springboot.skyproauction.dto.Lot();
        lotDto.setStatus(lot.getStatus());
        lotDto.setTitle(lot.getTitle());
        lotDto.setDescribtion(lot.getDescribtion());
        lotDto.setStartPrice(lot.getStartPrice());
        lotDto.setBidPrice(lot.getBidPrice());

        return lotDto;
    }
}
