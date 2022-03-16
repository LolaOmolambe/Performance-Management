package com.appraisal.modules.user.services.impl;

import com.appraisal.modules.user.services.UserService;
import com.appraisal.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean userExists(String emailAddress) {
        return userRepository.existsByEmail(emailAddress);
    }

}
