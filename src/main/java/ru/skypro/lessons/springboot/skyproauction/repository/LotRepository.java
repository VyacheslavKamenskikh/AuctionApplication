package ru.skypro.lessons.springboot.skyproauction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.skypro.lessons.springboot.skyproauction.dto.FullLot;
import ru.skypro.lessons.springboot.skyproauction.dto.Status;
import ru.skypro.lessons.springboot.skyproauction.entity.Lot;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Integer> {
    @Query(value = """
        SELECT  l.id as id,
            l.status as status,
            l.title as title,
            l.describtion as describtion,
            l.start_price as startPrice,
            l.bid_price as bidPrice,
            l.start_price +l.bid_price * (SELECT count(b.id) FROM bids b WHERE b.lot_id = :lotId) as currentPrice,
            q.name as bidderName,
            q.date_time as bidDate
        FROM lots l,(SELECT b.name, b.date_time FROM bids b WHERE b.lot_id =:lotId ORDER BY b.date_time DESC LIMIT 1) q
        WHERE l.id = :lotId
    """, nativeQuery = true)
    Optional<FullLot> getFullLot(@Param("lotId") int lotId);

    Page<Lot> findAllByStatus(Status status, Pageable pageable);
}
