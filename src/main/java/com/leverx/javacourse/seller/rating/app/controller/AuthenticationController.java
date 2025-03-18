package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Comment;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.mapper.CommentMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.EmailService;
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
    private final CommentMapper commentMapper;
    private final EmailService emailService;


    public AuthenticationController(UserService userService, CommentService commentService, UserMapper userMapper, CommentMapper commentMapper, EmailService emailService) {
        this.userService = userService;
        this.commentService = commentService;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        User user = userMapper.toSeller(userCreateDto);
        User newUser = userService.createUser(user);
        emailService.notifyAdmin("Registration request", String.format("New user \n %s", user));
        UserResponseDto responseDto = userMapper.toUserResponseDto(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/register_anon")
    public ResponseEntity<UserResponseDto> createAnonymousSeller(@RequestBody UserCreateDto userCreateDto) {
        User user = userService.createUser(userMapper.toSeller(userCreateDto));
        User newUser = userService.createUser(user);
        Comment comment = commentService.setComment(commentMapper.toComment(userCreateDto.getAssignedComments().getFirst())
                , user.getId());
        Comment newComment = commentService.save(comment);
        emailService.notifyAdmin("Registration request", String.format("New user \n %s \n with comment %s"
                , newUser, newComment));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponseDto(user));
    }
}
