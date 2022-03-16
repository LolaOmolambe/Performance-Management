package com.appraisal.modules.user.services.impl;

import com.appraisal.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenUserExistsByUsername() {
        when(userRepository.existsByEmailAddress("test@test.com"))
                .thenReturn(true);

        boolean userExists = userService.userExists("test@test.com");

        assertTrue(userExists);
    }

    @Test
    public void whenUserDoesNotExistByUsername() {
        when(userRepository.existsByEmailAddress("test@test.com"))
                .thenReturn(false);

        boolean userExists = userService.userExists("test@test.com");

        assertFalse(userExists);
    }
}