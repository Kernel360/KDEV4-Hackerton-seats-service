package org.seats.seat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyRequest {
    private Long seatId;

    private String startTime;

}
