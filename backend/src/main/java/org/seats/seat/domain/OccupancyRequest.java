package org.seats.seat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyRequest {

    // TODO JWT 적용 후 삭제
    private Long userId;

    private Long id; // 수정을 위한 id

    private Long seatId;

    private String startTime;

}
