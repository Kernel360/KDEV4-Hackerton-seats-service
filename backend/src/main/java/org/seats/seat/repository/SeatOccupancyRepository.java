package org.seats.seat.repository;

import java.time.LocalDateTime;
import org.seats.seat.entity.Seat;
import org.seats.seat.entity.SeatOccupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatOccupancyRepository extends JpaRepository<SeatOccupancy, Long> {

    // 예약된 날짜와 시간 사이에 다른 예약이 있는지 확인하는 쿼리
    @Query("SELECT COUNT(o) FROM SeatOccupancy o WHERE o.seat = :seat " +
        "AND o.startTime >= :startOfDay AND o.startTime < :endOfDay")
    long countBySeatAndStartTimeBetween(
        @Param("seat") Seat seat,
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay
    );


}
