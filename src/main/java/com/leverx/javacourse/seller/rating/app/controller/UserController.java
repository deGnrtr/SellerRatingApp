package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.CommentCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.CommentResponseDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.model.Comment;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.mapper.CommentDtoMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserDtoMapper;
import com.leverx.javacourse.seller.rating.app.entity.model.Seller;
import com.leverx.javacourse.seller.rating.app.entity.model.User;
import com.leverx.javacourse.seller.rating.app.entity.model.UserRoles;
import com.leverx.javacourse.seller.rating.app.service.CommentService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CommentService commentService;
    private final UserDtoMapper userDtoMapper;
    private final CommentDtoMapper commentDtoMapper;

    public UserController(UserService userService, CommentService commentService, UserDtoMapper userDtoMapper, CommentDtoMapper commentDtoMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.userDtoMapper = userDtoMapper;
        this.commentDtoMapper = commentDtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User requestedUser = userService.findById(id);
        UserResponseDto userResponseDto = userDtoMapper.toUserResponseDto(requestedUser);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllSellers(@RequestParam(required = false) String gameTitle,
                                                               @RequestParam(required = false) BigDecimal begin,
                                                               @RequestParam(required = false) BigDecimal end) {
        List<Seller> allUsers = userService.getAllSellers(gameTitle, begin, end);
        return ResponseEntity.status(HttpStatus.OK).body(userDtoMapper.sellerToUserResponseDtoList(allUsers));
    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentCreateDto commentCreateDto, @PathVariable Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment newComment = commentDtoMapper.toComment(commentCreateDto);
        newComment.setSeller(userService.findById(id));
        newComment.setAuthor(userService.findByLogin(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new));
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
