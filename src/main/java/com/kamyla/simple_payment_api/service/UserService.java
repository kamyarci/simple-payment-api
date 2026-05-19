package com.kamyla.simple_payment_api.service;

import com.kamyla.simple_payment_api.domain.exception.DuplicateUserException;
import com.kamyla.simple_payment_api.dto.UserDTO;
import com.kamyla.simple_payment_api.domain.exception.InsufficientBalanceException;
import com.kamyla.simple_payment_api.domain.exception.MerchantTransactionException;
import com.kamyla.simple_payment_api.domain.exception.UserNotFoundException;
import com.kamyla.simple_payment_api.domain.user.User;
import com.kamyla.simple_payment_api.domain.user.UserType;
import com.kamyla.simple_payment_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new MerchantTransactionException();
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
    }

    public User findUserById(Long id) {
        return this.userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(UserDTO user) {
        if (userRepository.findUserByDocument(user.document()).isPresent()) {
            throw new DuplicateUserException("CPF já cadastrado.");
        }

        if (userRepository.findUserByEmail(user.email()).isPresent()) {
            throw new DuplicateUserException("Email já cadastrado.");
        }

        User newUser = new User(user);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
