package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.CommentCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.entity.model.Seller;
import com.leverx.javacourse.seller.rating.app.entity.model.User;
import com.leverx.javacourse.seller.rating.app.mapper.CommentMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;
    private final UserMapper userMapper;
    private final CommentMapper commentDtoMapper;

    public UserController(UserService userService, CommentService commentService, UserMapper userMapper, CommentMapper commentDtoMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.userMapper = userMapper;
        this.commentDtoMapper = commentDtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User requestedUser = userService.findById(id);
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(requestedUser);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllSellers(@RequestParam(required = false) String gameTitle,
                                                               @RequestParam(required = false) BigDecimal begin,
                                                               @RequestParam(required = false) BigDecimal end) {
        List<Seller> allUsers = userService.getAllSellers(gameTitle, begin, end);
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.sellerToUserResponseDtoList(allUsers));
    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentCreateDto commentCreateDto, @PathVariable Long id) {
        Comment Comment = commentDtoMapper.toComment(commentCreateDto);
        Comment newComment = commentService.setComment(Comment, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoMapper.toCommentResponseDto(commentService.save(newComment)));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllSellerComments(@PathVariable Long id) {
        Seller requestedUser = (Seller) userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(commentDtoMapper.toCommentResponseDtoList(requestedUser.getAssignedComments()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
