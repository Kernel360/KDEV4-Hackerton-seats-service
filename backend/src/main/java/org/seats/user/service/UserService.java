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
}
