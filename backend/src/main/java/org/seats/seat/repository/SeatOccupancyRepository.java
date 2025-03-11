package org.seats.seat.repository;

import java.time.LocalDateTime;
import java.util.List;
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

    // 특정 시간 범위에 예약된 좌석들을 조회
    List<SeatOccupancy> findByStartTimeBetween(LocalDateTime startOfPeriod, LocalDateTime endOfPeriod);

    // 유저가 예약한 예약 목록을 조회
    List<SeatOccupancy> findByUserId(Long userId);

}
