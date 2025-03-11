package org.seats.seat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OccupancyListResponse {

    private Long userId;                // 예약한 user

    private Long seatId;                // 예약한 자리 id

    private String seatName;            // 예약한 자리 이름

    private String startTime;           // 예약 시간

}
