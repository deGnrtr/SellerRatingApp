package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Comment;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.mapper.CommentMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {

    private final UserService userService;
    private final CommentService commentService;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

    public AdministratorController(UserService userService, CommentService commentService, UserMapper userMapper, CommentMapper commentMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/all-unverified-users")
    public ResponseEntity<List<UserResponseDto>> getAllUnverifiedUsers(@RequestParam String role) {
        List<UserResponseDto> responseDtoList = null;
        if (role.equals("seller")) {
            List<Seller> requestedSellers = userService.getAllSellers(null, null, null, "NOT_VERIFIED");
            responseDtoList = userMapper.sellerToUserResponseDtoList(requestedSellers);
        } else if (role.equals("visitor")) {
            List<Visitor> requestedVisitors = userService.getAllVisitors("NOT_VERIFIED");
            responseDtoList = userMapper.visitorToUserResponseDtoList(requestedVisitors);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/all-unverified-comments")
    public ResponseEntity<List<CommentResponseDto>> getAllUnverifiedComments() {
        List<Comment> requestedComments = commentService.findAllComments("NOT_VERIFIED");
        return ResponseEntity.status(HttpStatus.OK).body(commentMapper.toCommentResponseDtoList(requestedComments));
    }

    @GetMapping("/user-verify")
    public ResponseEntity<UserResponseDto> verifyUser(@RequestParam Long id){
        UserResponseDto userResponseDto = null;
        User updatedUser = userService.updateUser(id, userService.verifyUser(id));
        if (UserRoles.SELLER.equals(updatedUser.getRole())){
            userResponseDto = userMapper.sellerToUserResponseDto((Seller) updatedUser);
        }else if (UserRoles.VISITOR.equals(updatedUser.getRole())){
            userResponseDto = userMapper.visitorToUserResponseDto((Visitor) updatedUser);}
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @DeleteMapping("/user-refuse")
    public ResponseEntity<String> refuseUser(@RequestParam Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/comment-verify")
    public ResponseEntity<CommentResponseDto> verifyComment(@RequestParam Long id){
        Comment verifiedComment = commentService.updateComment(id, commentService.verifyComment(id));
        Seller targetSeller = (Seller) verifiedComment.getSeller();
        userService.updateRating(targetSeller, verifiedComment.getRatingFromComment());
        return ResponseEntity.status(HttpStatus.OK).body(commentMapper.toCommentResponseDto(verifiedComment));
    }

    @DeleteMapping("/comment-refuse")
    public ResponseEntity<String> refuseComment(@RequestParam Long id){
        commentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
