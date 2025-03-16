package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.entity.model.Seller;
import com.leverx.javacourse.seller.rating.app.entity.model.User;
import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.mapper.UserDtoMapper;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final CommentService commentService;
    private final UserDtoMapper userDtoMapper;


    public AuthenticationController(UserService userService, CommentService commentService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.userDtoMapper = userDtoMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        User user = null;
        if (UserRoles.SELLER.toString().equals(userCreateDto.getRole())) {
            user = userService.saveSeller(userDtoMapper.toSeller(userCreateDto));
        } else if (UserRoles.VISITOR.toString().equals(userCreateDto.getRole())) {
            user = userService.saveVisitor(userDtoMapper.toVisitor(userCreateDto));
        }
        UserResponseDto responseDto = userDtoMapper.toUserResponseDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/register_anon")
    public ResponseEntity<UserResponseDto> createAnonymousSeller(@RequestBody UserCreateDto userCreateDto, @AuthenticationPrincipal UserDetails userDetails) {
        Seller seller = userService.saveSeller(userDtoMapper.toSeller(userCreateDto));
        Comment comment = seller.getAssignedComments().getFirst();
        comment.setSeller(seller);
        comment.setAuthor(userService.findByLogin(userDetails.getUsername()).get());
        commentService.save(comment);
        UserResponseDto responseDto = userDtoMapper.toUserResponseDto(userService.findById(seller.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
