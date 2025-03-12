package org.seats.seat.domain;

import java.util.List;

import org.seats.seat.domain.table.TableData;
import org.seats.seat.domain.table.TableHeader;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OccupancyTableResponse {

	private TableHeader tableHeader;
	private List<TableData> tableData;
}
