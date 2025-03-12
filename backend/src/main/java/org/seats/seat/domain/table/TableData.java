package org.seats.seat.domain.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TableData {
	private String startTime;
	private String endTime;
	private SeatStatus seatA;
	private SeatStatus seatB;
	private SeatStatus seatC;
	private SeatStatus seatD;
}


