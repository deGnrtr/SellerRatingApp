package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.entity.model.User;
import com.leverx.javacourse.seller.rating.app.mapper.CommentMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final CommentService commentService;
    private final UserMapper userMapper;
    private final CommentMapper commentDtoMapper;


    public AuthenticationController(UserService userService, CommentService commentService, UserMapper userMapper, CommentMapper commentDtoMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.userMapper = userMapper;
        this.commentDtoMapper = commentDtoMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        User user = userMapper.toUser(userCreateDto);
        UserResponseDto responseDto = userMapper.toUserResponseDto(userService.createUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/register_anon")
    public ResponseEntity<UserResponseDto> createAnonymousSeller(@RequestBody UserCreateDto userCreateDto) {
        User seller = userService.createUser(userMapper.toUser(userCreateDto));
        Comment comment = commentService.setComment(commentDtoMapper.toComment(userCreateDto.getAssignedComments().getFirst())
                , seller.getId());
        commentService.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponseDto(seller));
    }
}
