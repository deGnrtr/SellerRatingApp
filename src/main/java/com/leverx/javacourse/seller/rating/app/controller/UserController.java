package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.CommentCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.mapper.UserDtoMapper;
import com.leverx.javacourse.seller.rating.app.entity.model.Seller;
import com.leverx.javacourse.seller.rating.app.entity.model.User;
import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User requestedUser = userService.findById(id);
        UserResponseDto userResponseDto = userDtoMapper.toUserResponseDto(requestedUser);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllSellersRanked() {
        List<Seller> allUsers = userService.getSellerByRating();
        return ResponseEntity.status(HttpStatus.OK).body(userDtoMapper.sellerToUserResponseDtoList(allUsers));
    }

    /*@GetMapping("/all/by_game")
    public ResponseEntity<List<UserResponseDto>> getUsersByGame(@RequestParam String gameTitle) {
        List<Seller> usersByGame = userService.getSellersByGame(gameTitle);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoMapper.sellerToUserResponseDtoList(usersByGame));
    }

    @GetMapping("/all/in_range")
    public ResponseEntity<List<UserResponseDto>> getUsersInRatingRange(@RequestParam BigDecimal begin, @RequestParam BigDecimal end) {
        List<Seller> requestedUsers = userService.getSellersInRatingRange(begin, end);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoMapper.sellerToUserResponseDtoList(requestedUsers));
    }*/

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponseDto> saveComment(@RequestBody CommentCreateDto commentCreateDto, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment newComment = commentDtoMapper.toComment(commentCreateDto);
        newComment.setSeller(userService.findById(id));
        newComment.setAuthor(userService.findByLogin(userDetails.getUsername()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoMapper.toCommentResponseDto(commentService.save(newComment)));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<Comment> comments = commentService.findAllComments();
        return ResponseEntity.status(HttpStatus.OK).body(commentDtoMapper.toCommentResponseDtoList(comments));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUsername().equals(userService.findById(id).getLogin()) || userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .anyMatch(a -> UserRoles.ADMINISTRATOR.getAuthority().equals(a))){
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
