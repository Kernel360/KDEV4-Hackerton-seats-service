package org.seats.seat.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.seats.seat.converter.SeatOccupancyConverter;
import org.seats.seat.domain.MyOccupancyListResponse;
import org.seats.seat.domain.OccupancyListResponse;
import org.seats.seat.domain.OccupancyRequest;
import org.seats.seat.domain.OccupancyResponse;
import org.seats.seat.domain.OccupancyTableResponse;
import org.seats.seat.domain.table.SeatResponse;
import org.seats.seat.domain.table.SeatStatus;
import org.seats.seat.domain.table.TableData;
import org.seats.seat.domain.table.TableHeader;
import org.seats.seat.entity.Seat;
import org.seats.seat.entity.SeatOccupancy;
import org.seats.seat.repository.SeatOccupancyRepository;
import org.seats.seat.repository.SeatRepository;
import org.seats.user.entity.User;
import org.seats.user.entity.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatOccupancyService {

	private final SeatOccupancyRepository seatOccupancyRepository;
	private final SeatRepository seatRepository;
	private final UserRepository userRepository;
	private final SeatOccupancyConverter seatOccupancyConverter;

	// 예약 리스트 전체 조회
	public OccupancyTableResponse getOccupancyTable(LocalDate date) {
		TableHeader header = createTableHeader();
		List<TableData> datas = createTableData(date);

		return new OccupancyTableResponse(header, datas);
	}

	private List<TableData> createTableData(LocalDate date) {
		// 09시부터 10시, 10시부터 11시, ... 19시부터 20시까지 데이터가 1시간 간격으로 생성 되어야 함.
		List<Seat> seatList = seatRepository.findAll();

		Seat seatA = seatList.get(0);
		Seat seatB = seatList.get(1);
		Seat seatC = seatList.get(2);
		Seat seatD = seatList.get(3);

		List<TableData> datas = new ArrayList<>();

		for (int hour = 9; hour < 20; hour++) {
			// 종료 시간이 20시가 되어야 하므로, hour가 19일 때 endTime은 20시가 됨.
			String startTime = String.format("%02d:00", hour);
			String endTime = String.format("%02d:00", hour + 1);

			// 만약 현재 시간이 14시인 경우 14시 이전 시간은 예약할 수 없다
			int currentHour = LocalDateTime.now().getHour();
			boolean isOccupancy = hour < currentHour;

			TableData tableData = new TableData(
				date,
				startTime,
				endTime,
				new SeatStatus(seatA.getId(), seatA.getName(), isOccupancy),
				new SeatStatus(seatB.getId(), seatB.getName(), isOccupancy),
				new SeatStatus(seatC.getId(), seatC.getName(), isOccupancy),
				new SeatStatus(seatD.getId(), seatD.getName(), isOccupancy)
			);
			datas.add(tableData);
		}

		// 해당 날짜의 시작과 끝을 LocalDateTime으로 변환
		LocalDateTime startOfDay = date.atStartOfDay();  // 00:00
		LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 23:59:59.999999999

		List<SeatOccupancy> seatOccupancyList = seatOccupancyRepository.findByStartTimeBetween(startOfDay, endOfDay);
		for (SeatOccupancy occupancy : seatOccupancyList) {
			int occupancyHour = occupancy.getStartTime().getHour();
			int tableDataIndex = occupancyHour - 9; // 09시 -> index 0, etc.

			TableData tableData = datas.get(tableDataIndex);
			Long occupancySeatId = occupancy.getSeat().getId();

			if (occupancySeatId.equals(seatA.getId())) {
				tableData.getSeatA().setOccupancy(true);
			} else if (occupancySeatId.equals(seatB.getId())) {
				tableData.getSeatB().setOccupancy(true);
			} else if (occupancySeatId.equals(seatC.getId())) {
				tableData.getSeatC().setOccupancy(true);
			} else if (occupancySeatId.equals(seatD.getId())) {
				tableData.getSeatD().setOccupancy(true);
			}
		}

		return datas;
	}

	private TableHeader createTableHeader() {
		List<Seat> seatList = seatRepository.findAll();

		SeatResponse seatA = new SeatResponse(seatList.get(0).getId(), seatList.get(0).getName());
		SeatResponse seatB = new SeatResponse(seatList.get(1).getId(), seatList.get(1).getName());
		SeatResponse seatC = new SeatResponse(seatList.get(2).getId(), seatList.get(2).getName());
		SeatResponse seatD = new SeatResponse(seatList.get(3).getId(), seatList.get(3).getName());

		return new TableHeader(
			"시간",
			seatA,
			seatB,
			seatC,
			seatD
		);
	}

	// 실시간 예약 현황 보기
	public List<OccupancyListResponse> getNowOccupanyList() {
		// 현재 시간
		LocalDateTime now = LocalDateTime.now();

		// 현재 시간의 시간 범위
		LocalDateTime startOfCurrentHour = now.withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endOfCurrentHour = startOfCurrentHour.plusHours(1);

		// 해당 시간대에 예약된 모든 좌석을 조회
		List<SeatOccupancy> seatOccupancies = seatOccupancyRepository.findByStartTimeBetween(startOfCurrentHour,
			endOfCurrentHour);

		// 날짜 형식 정의
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		// SeatOccupancy 목록을 OccupancyListResponse 목록으로 변환
		return seatOccupancies.stream()
			.map(occupancy -> {
				// 좌석 이름
				String seatName = occupancy.getSeat().getName();
				String formattedStartTime = occupancy.getStartTime().format(formatter);

				return new OccupancyListResponse(
					occupancy.getUser().getId(),
					occupancy.getSeat().getId(),
					seatName,
					formattedStartTime
				);
			})
			.collect(Collectors.toList());
	}

	// 유저의 예약 리스트 조회
	public List<MyOccupancyListResponse> getMyOccupancyList(Long userId) {
		// 예외 처리
		if (userId == null) {
			throw new IllegalStateException("User ID is missing. Please ensure the JWT token is provided.");
		}

		List<SeatOccupancy> list = seatOccupancyRepository.findByUserId(userId);

		return list.stream()
			.map(seatOccupancyConverter::toMyOccupancyListResponse)
			.collect(Collectors.toList());
	}

	// 예약 하기
	public OccupancyResponse createOccupancy(OccupancyRequest request, Long userId) {
		// 요청된 시간대 시작 시간 파싱
		LocalDateTime startDateTime = LocalDateTime.parse(request.getStartTime(),
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		LocalDate requestedDate = startDateTime.toLocalDate();  // 요청된 날짜

		// 예외 처리
		if (userId == null) {
			throw new IllegalStateException("User ID is missing. Please ensure the JWT token is provided.");
		}

		// 유저 확인
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

		// 좌석 정보 조회
		Seat seat = seatRepository.findById(request.getSeatId())
			.orElseThrow(() -> new RuntimeException("Seat Not Found"));

		// 날짜 범위 설정 (하루의 시작과 끝)
		LocalDateTime startOfDay = requestedDate.atStartOfDay();  // 하루의 시작
		LocalDateTime endOfDay = requestedDate.plusDays(1).atStartOfDay();  // 하루의 끝 (다음날 00:00)

		// 로그인한 사용자가 하루에 예약 2번 하는지 확인
		checkUserBookingLimit(user, startOfDay, endOfDay);

		// 같은 시간에 이미 다른 예약이 존재하는지 확인
		checkTimeSlotAvailability(seat, startDateTime);

		// 좌석 예약 생성
		SeatOccupancy occupancy = seatOccupancyConverter.toEntity(request, userId);
		SeatOccupancy newOccupancy = seatOccupancyRepository.save(occupancy);

		// 예약 성공 시, 응답 객체로 변환하여 반환
		return seatOccupancyConverter.toResponse(newOccupancy);
	}

	// 예약이 하루에 2번 초과하는지 확인하는 메서드 (조건 1)
	private void checkUserBookingLimit(User user, LocalDateTime startOfDay, LocalDateTime endOfDay) {
		long userBookingCount = seatOccupancyRepository.countByUserAndStartTimeBetween(user, startOfDay, endOfDay);

		// 사용자가 하루에 2번 예약을 시도하는지 체크
		if (userBookingCount >= 2) {
			log.error("User {} has already booked twice on this day.", user.getId());
			throw new IllegalStateException("하루에 2번 예약 가능합니다.");
		}
	}

	// 동일한 시간에 다른 사용자가 이미 예약한 경우를 체크하는 메서드 (조건 2)
	private void checkTimeSlotAvailability(Seat seat, LocalDateTime startDateTime) {
		long conflictingBookings = seatOccupancyRepository.countBySeatAndStartTime(seat, startDateTime);

		// 동일한 시간에 두 명 이상 예약할 수 없도록 체크
		if (conflictingBookings > 0) {
			log.error("The seat {} is already booked at {}.", seat.getId(), startDateTime);
			throw new IllegalStateException("다른 사용자들이 이미 같은 시간에 예약을 했습니다.");
		}
	}

	// 삭제하기
	public void delete(Long seatId, LocalDateTime startTime, Long userId) {
		SeatOccupancy seatOccupancy = seatOccupancyRepository.findBySeatIdAndUserIdAndStartTime(
			seatId, userId, startTime);
		if (seatOccupancy == null) {
			throw new IllegalStateException("예약을 찾을 수 없습니다.");
		}
		seatOccupancyRepository.deleteById(seatOccupancy.getId());
	}

}
