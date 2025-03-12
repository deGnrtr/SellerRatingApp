package com.leverx.javacourse.seller_rating_app.service;

import com.leverx.javacourse.seller_rating_app.entity.model.Comment;
import com.leverx.javacourse.seller_rating_app.entity.model.Item;
import com.leverx.javacourse.seller_rating_app.entity.model.User;
import com.leverx.javacourse.seller_rating_app.entity.model.UserRoles;
import com.leverx.javacourse.seller_rating_app.exception.EntityNotFoundException;
import com.leverx.javacourse.seller_rating_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private CommentService commentService;
    private ItemService itemService;

    public UserService(UserRepository repository, CommentService commentService, ItemService itemService) {
        this.userRepository = repository;
        this.commentService = commentService;
        this.itemService = itemService;
    }

    @Transactional
    public User findById(Long id) {
        Optional<User> requestedUser = userRepository.findById(id);
        return requestedUser.orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public User save(User user) {
        User saved = new User();
        if (user.getUserRole().equals(UserRoles.SELLER)){
            saved = userRepository.save(user);
            if (saved.getComments() != null){
                for(Comment comment: saved.getComments()){
                    comment.setAuthor(user);
                    commentService.save(comment);
                }
            }
        } else if (user.getUserRole().equals(UserRoles.SIMPLE_USER)){
            user.getComments().clear();
            saved = userRepository.save(user);
        }
        return saved;
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public List<User> getUsersByRating() {
        return userRepository.findByRatingOrderByRatingDesc();
    }

    @Transactional
    public List<User> getUsersInRatingRange(BigDecimal begin, BigDecimal ends) {
        return userRepository.findByRatingRange(begin, ends);
    }

    @Transactional
    public List<User> getUsersByGame(String gameTitle){
        List<Item> selectedItems = itemService.findByGameTitle(gameTitle);
        List<User> requestedUsers = new ArrayList<>();
        for (Item selectedItem : selectedItems) {
            if (selectedItem.getSeller().getUserRole().equals(UserRoles.SELLER)) {
                requestedUsers.add(findById(selectedItem.getSeller().getId()));
            }
        }
        return requestedUsers;
    }
}
