package org.seats.seat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OccupancyResponse {

    // TODO JWT 적용 후 삭제
    private Long userId;

    private Long seatId;

    private String startTime;

}
