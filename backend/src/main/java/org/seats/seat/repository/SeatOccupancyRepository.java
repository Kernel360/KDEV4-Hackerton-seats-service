package org.seats.seat.repository;

import java.time.LocalDateTime;
import org.seats.seat.entity.Seat;
import org.seats.seat.entity.SeatOccupancy;
import org.seats.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatOccupancyRepository extends JpaRepository<SeatOccupancy, Long> {

    // 특정 사용자가 하루에 2번 이상 예약했는지 확인
    long countByUserAndStartTimeBetween(User user, LocalDateTime startOfDay, LocalDateTime endOfDay);

    // 특정 좌석에 대해 동일한 startTime 으로 예약된 건수를 확인
    long countBySeatAndStartTime(Seat seat, LocalDateTime startTime);

}
