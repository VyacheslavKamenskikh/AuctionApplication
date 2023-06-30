package ru.skypro.lessons.springboot.skyproauction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.skyproauction.dto.FullLot;
import ru.skypro.lessons.springboot.skyproauction.entity.Bid;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Integer> {
    Optional<Bid> findFirstByLot_IdOrderByDateTimeAsc(int lotId);

    @Query("""
        SELECT new ru.skypro.lessons.springboot.skyproauction.dto.Bid(b.name, b.dateTime) FROM Bid b WHERE b.name = (
        SELECT b.name FROM Bid b GROUP BY b.name ORDER BY COUNT(b.name) DESC LIMIT 1) ORDER BY b.dateTime DESC LIMIT 1
    """)
    Optional<ru.skypro.lessons.springboot.skyproauction.dto.Bid> findTheMostFrequentBidder(@Param("lotId") int lotId);


}
