package org.seats.seat.converter;

import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.entity.Seat;
import org.seats.seat.entity.SeatOccupancy;
import org.seats.seat.repository.SeatRepository;
import org.seats.user.entity.User;
import org.seats.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeatOccupancyConverter {
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    public SeatOccupancy toEntity(OccupancyRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User Not Found"));

        Seat seat = seatRepository.findById(request.getSeatId())
            .orElseThrow(() -> new RuntimeException("Seat Not Found"));

        return SeatOccupancy.builder()
            .user(user)
            .seat(seat)
            .startTime(LocalDateTime.parse(request.getStartTime(), formatter))
            .build();
    }

    public OccupancyResponse toResponse(SeatOccupancy seatOccupancy) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return OccupancyResponse.builder()
            .userId(seatOccupancy.getUser().getId())
            .seatId(seatOccupancy.getSeat().getId())
            .startTime(seatOccupancy.getStartTime().format(formatter))
            .build();
    }

}
