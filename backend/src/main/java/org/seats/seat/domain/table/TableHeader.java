package org.seats.seat.domain.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TableHeader {
	private String time;
	private SeatResponse seatA;
	private SeatResponse seatB;
	private SeatResponse seatC;
	private SeatResponse seatD;
}
