package com.leverx.javacourse.seller_rating_app.controller;

import com.leverx.javacourse.seller_rating_app.entity.dto.UserCreateDto;
import com.leverx.javacourse.seller_rating_app.entity.dto.UserResponseDto;
import com.leverx.javacourse.seller_rating_app.entity.dto_mappers.UserDtoMapper;
import com.leverx.javacourse.seller_rating_app.entity.model.*;
import com.leverx.javacourse.seller_rating_app.service.CommentService;
import com.leverx.javacourse.seller_rating_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final CommentService commentService;
    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService, CommentService commentService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        User requestedUser = userService.findById(id);
        UserResponseDto userResponseDto = userDtoMapper.toUserResponseDto(requestedUser);
        return ResponseEntity.status(HttpStatus.FOUND).body(userResponseDto);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto, @AuthenticationPrincipal UserDetails userDetails){
        UserResponseDto responseDto = null;
        if (userDetails.getAuthorities().stream().findFirst().get().toString().equals(UserRoles.SELLER.getAuthority())){
            Seller seller = userService.saveSeller(userDtoMapper.toSeller(userCreateDto));
            responseDto = userDtoMapper.toUserResponseDto(seller);
        } else if (userCreateDto.getRole().equals(UserRoles.VISITOR.toString())) {
            Visitor visitor = userService.saveVisitor(userDtoMapper.toVisitor(userCreateDto));
            responseDto = userDtoMapper.toUserResponseDto(visitor);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/auth/register_anon")
    public ResponseEntity<UserResponseDto> createAnonymousSeller(@RequestBody UserCreateDto userCreateDto, @RequestParam Long authorId){
        Seller seller = userService.saveSeller(userDtoMapper.toSeller(userCreateDto));
        Comment comment = seller.getAssignedComments().getFirst();
        comment.setSeller(seller);
        comment.setAuthor(userService.findById(authorId));
        commentService.save(comment);
        UserResponseDto responseDto = userDtoMapper.toUserResponseDto(userService.findById(seller.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserResponseDto>> getAllSellersRanked(){
        List<Seller> allUsers = userService.getSellerByRating();
        return ResponseEntity.status(HttpStatus.FOUND).body(userDtoMapper.sellerToUserResponseDtoList(allUsers));
    }

    @GetMapping("/users/all/by_game")
    public ResponseEntity<List<UserResponseDto>> getUsersByGame(@RequestParam String gameTitle){
        List<Seller> usersByGame = userService.getSellersByGame(gameTitle);
        return ResponseEntity.status(HttpStatus.FOUND).body(userDtoMapper.sellerToUserResponseDtoList(usersByGame));
    }

    @GetMapping("/users/all/in_range")
    public ResponseEntity<List<UserResponseDto>> getUsersInRatingRange(@RequestParam BigDecimal begin, @RequestParam BigDecimal end){
        List<Seller> requestedUsers = userService.getSellersInRatingRange(begin, end);
        return ResponseEntity.status(HttpStatus.FOUND).body(userDtoMapper.sellerToUserResponseDtoList(requestedUsers));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        User user = userService.findById(id);
        if (user.getId().equals(id)){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
