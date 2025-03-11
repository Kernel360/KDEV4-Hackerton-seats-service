package org.seats.user.service;

import lombok.RequiredArgsConstructor;
import org.seats.user.entity.User;
import org.seats.user.entity.UserRepository;
import org.seats.user.model.UserRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SeatOccupancyRepository seatOccupancyRepository;
    private final SeatOccupancyConverter seatOccupancyConverter;

    public User signup(
            UserRequest userRequest
    ){
        var entity = User.builder()
                .userName(userRequest.getName())
                .password(userRequest.getPassword())
                .build()
                ;

        return userRepository.save(entity);
    }

    public User signin(
            UserRequest userRequest
    ){
        return userRepository.findByName(userRequest.getName())
                .map(it -> {
                    if (!it.getPassword().equals(userRequest.getPassword())) {
                        var format = "패스워드를 잘못 입력하였습니다. %s vs %s";
                        throw new RuntimeException(String.format(format, it.getPassword(), userRequest.getPassword()));
                    }

                    return it;
                }).orElseThrow(
                        () -> {
                            return new RuntimeException("아이디가 존재하지 않습니다. : "+userRequest.getName());
                        }
                );
    }

    public User delete(
            UserRequest userRequest
    ){
        return userRepository.findByName(userRequest.getName())
                .map(it -> {
//                    if (!it.getPassword().equals(userRequest.getPassword())) {
//                        var format = "패스워드를 잘못 입력하였습니다. %s vs %s";
//                        throw new RuntimeException(String.format(format, it.getPassword(), userRequest.getPassword()));
//                    }

                    userRepository.delete(it);
                    return it;
                }).orElseThrow(
                        () -> {
                            return new RuntimeException("호출될 일은 없지만 일단 기록함.");
                        }
                );
    }

    public List<MyOccupancyListResponse> getMyOccupancyList(Long userId) {
        // 유저가 예약한 모든 예약 목록을 가져오기
        List<SeatOccupancy> occupancyList = seatOccupancyRepository.findByUserId(userId);

        // 예약 목록을 MyOccupancyListResponse로 변환하여 반환
        return occupancyList.stream()
            .map(seatOccupancyConverter::toMyOccupancyListResponse)
            .collect(Collectors.toList());
    }
}
