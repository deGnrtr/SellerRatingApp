package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.ReviewCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.ReviewResponseDto;
import com.leverx.javacourse.seller.rating.app.dto.UserCreateDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.mapper.ReviewMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.service.EmailService;
import com.leverx.javacourse.seller.rating.app.service.ReviewService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, ReviewService reviewService, UserMapper userMapper, ReviewMapper reviewMapper, EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User requestedUser = userService.findByIdAndStatus(id, "VERIFIED");
        UserResponseDto userResponseDto = null;
        if (requestedUser instanceof Seller){
            userResponseDto = userMapper.sellerToUserResponseDto((Seller) requestedUser);
        }else if (requestedUser instanceof Visitor){
            userResponseDto = userMapper.visitorToUserResponseDto((Visitor) requestedUser);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllSellers(@RequestParam(required = false) String gameTitle,
                                                               @RequestParam(required = false) BigDecimal begin,
                                                               @RequestParam(required = false) BigDecimal end) {
        List<Seller> allUsers = userService.getAllSellers(gameTitle, begin, end, "VERIFIED");
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.sellerToUserResponseDtoList(allUsers));
    }

    //FIXME stackowerflow
    @PostMapping("/{id}/review")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponseDto> addReview(@RequestBody ReviewCreateDto reviewCreateDto, @PathVariable Long id) {
        Review Review = reviewMapper.toReview(reviewCreateDto);
        Review newReview = reviewService.setReview(Review, id);
        emailService.notifyAdmin("New review", String.format("New review added\n %s", newReview));
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.toReviewResponseDto(reviewService.save(newReview)));
    }

    @GetMapping("/{id}/review")
    public ResponseEntity<List<ReviewResponseDto>> getAllSellerReviews(@PathVariable Long id) {
        Seller requestedUser = (Seller) userService.findByIdAndStatus(id, "VERIFIED");
        List<Review> requestedReviews = requestedUser.getAssignedReviews().stream()
                .filter(a -> a.getStatus().equals("VERIFIED")).toList();
        return ResponseEntity.status(HttpStatus.OK).body(reviewMapper.toReviewResponseDtoList(requestedReviews));
    }

    //TODO check after creating
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //TODO check before deleting
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        UserResponseDto userResponseDto = null;
        if (userCreateDto.getRole().equals("SELLER")){
            userResponseDto = userMapper.sellerToUserResponseDto((Seller) userService.updateUser(id, userMapper
                    .toSeller(userCreateDto)));
        }else if (userCreateDto.getRole().equals("VISITOR")){
            userResponseDto = userMapper.visitorToUserResponseDto((Visitor) userService.updateUser(id, userMapper
                    .toVisitor(userCreateDto)));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
}
