package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.mapper.ReviewMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.service.ReviewService;
import com.leverx.javacourse.seller.rating.app.service.EmailService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, ReviewService reviewService, UserMapper userMapper, ReviewMapper reviewMapper, EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User newUser = userService.createUser(userCreateDto);
        emailService.notifyAdmin("Registration request", String.format("New user \n %s", newUser));
        UserResponseDto responseDto = null;
        if (UserRoles.SELLER.equals(newUser.getRole())){
            responseDto = userMapper.sellerToUserResponseDto((Seller) newUser);
        }else if (UserRoles.VISITOR.equals(newUser.getRole())){
            responseDto = userMapper.visitorToUserResponseDto((Visitor) newUser);}
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register_anon")
    public ResponseEntity<UserResponseDto> createAnonymousSeller(@RequestBody UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User newUser = userService.createUser(userCreateDto);
        Review review = reviewService.setReview(reviewMapper.toReview(userCreateDto.getAssignedReviews().getFirst())
                , newUser.getId());
        Review newReview = reviewService.save(review);
        emailService.notifyAdmin("Registration request", String.format("New user \n %s \n With review %s"
                , newUser, newReview));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.sellerToUserResponseDto((Seller) newUser));
    }
}
