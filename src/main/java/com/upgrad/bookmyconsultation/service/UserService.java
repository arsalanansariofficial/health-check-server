package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.User;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.provider.PasswordCryptographyProvider;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    UserRepository userRepository;
    PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordCryptographyProvider passwordCryptographyProvider) {
        this.userRepository = userRepository;
        this.passwordCryptographyProvider = passwordCryptographyProvider;
    }

    public User register(User user) throws InvalidInputException {
        ValidationUtils.validate(user);

        user.setCreatedDate(LocalDate.now().toString());
        encryptPassword(user);
        userRepository.save(user);
        return user;
    }

    public User getUser(String id) {
        return Optional.of(userRepository.findById(id)).get().orElseThrow(ResourceUnAvailableException::new);
    }

    /**
     * @return the list of all the users that are registered in the database
     * created by Arsalan Ansari
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void encryptPassword(final User newUser) {

        String password = newUser.getPassword();
        final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
        newUser.setSalt(encryptedData[0]);
        newUser.setPassword(encryptedData[1]);
    }
}