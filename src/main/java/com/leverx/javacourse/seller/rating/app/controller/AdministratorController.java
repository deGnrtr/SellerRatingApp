package com.leverx.javacourse.seller.rating.app.controller;

import com.leverx.javacourse.seller.rating.app.dto.ReviewResponseDto;
import com.leverx.javacourse.seller.rating.app.dto.UserResponseDto;
import com.leverx.javacourse.seller.rating.app.entity.Review;
import com.leverx.javacourse.seller.rating.app.entity.Seller;
import com.leverx.javacourse.seller.rating.app.entity.User;
import com.leverx.javacourse.seller.rating.app.entity.UserRoles;
import com.leverx.javacourse.seller.rating.app.entity.Visitor;
import com.leverx.javacourse.seller.rating.app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller.rating.app.mapper.ReviewMapper;
import com.leverx.javacourse.seller.rating.app.mapper.UserMapper;
import com.leverx.javacourse.seller.rating.app.service.ReviewService;
import com.leverx.javacourse.seller.rating.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministratorController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;

    public AdministratorController(UserService userService, ReviewService reviewService, UserMapper userMapper, ReviewMapper reviewMapper) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
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

    @GetMapping("/all-unverified-reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllUnverifiedComments() {
        List<Review> requestedReviews = reviewService.findAllReviews("NOT_VERIFIED");
        return ResponseEntity.status(HttpStatus.OK).body(reviewMapper.toReviewResponseDtoList(requestedReviews));
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

    @GetMapping("/user-refuse")
    public ResponseEntity<String> refuseUser(@RequestParam Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //TODO maybe @Transactional
    @GetMapping("/review-verify")
    public ResponseEntity<ReviewResponseDto> verifyComment(@RequestParam Long id){
        Review verifiedReview = reviewService.updateReview(id, reviewService.verifyReview(id));
        Seller targetSeller = (Seller) verifiedReview.getSeller();
        userService.updateRating(targetSeller, verifiedReview.getRatingFromReview());
        return ResponseEntity.status(HttpStatus.OK).body(reviewMapper.toReviewResponseDto(verifiedReview));
    }

    @GetMapping("/review-refuse")
    public ResponseEntity<String> refuseComment(@RequestParam Long id){
        if (reviewService.findById(id).getStatus().equals("NOT_VERIFIED")){
            reviewService.deleteById(id);
        }else throw new EntityNotFoundException("User has benn verified already.");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
