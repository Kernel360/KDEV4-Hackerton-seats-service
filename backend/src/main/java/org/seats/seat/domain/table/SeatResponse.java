package org.seats.seat.domain.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatResponse {
	private Long seatId;
	private String seatName;
}
