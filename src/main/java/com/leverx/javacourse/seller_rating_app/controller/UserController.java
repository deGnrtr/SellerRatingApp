package com.leverx.javacourse.seller_rating_app.controller;

import com.leverx.javacourse.seller_rating_app.entity.dto.UserCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.UserResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.dto_mappers.UserDtoMapper;
import com.leverx.javacourse.seller_rating_app.entity.model.User;
import com.leverx.javacourse.seller_rating_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        User requestedUser = userService.findById(id);
        UserResponseDto userResponseDto = userDtoMapper.toUserResponseDto(requestedUser);
        return ResponseEntity.status(HttpStatus.FOUND).body(userResponseDto);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto){
        User newUser = userService.save(userDtoMapper.toUser(userCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoMapper.toUserResponseDto(newUser));
//        return ResponseEntity.status(HttpStatus.CREATED).body(userCreateDto);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserResponseDto>> getAllSellersRanked(){
        List<User> allUsers = userService.getUsersByRating();
        return ResponseEntity.status(HttpStatus.FOUND).body(userDtoMapper.toUserResponseDtoList(allUsers));
    }

    @GetMapping("/users/all/by_game")
    public ResponseEntity<List<UserResponseDto>> getUsersByGame(@RequestParam String gameTitle){
        List<User> usersByGame = userService.getUsersByGame(gameTitle);
        return ResponseEntity.status(HttpStatus.FOUND).body(userDtoMapper.toUserResponseDtoList(usersByGame));
    }
}
