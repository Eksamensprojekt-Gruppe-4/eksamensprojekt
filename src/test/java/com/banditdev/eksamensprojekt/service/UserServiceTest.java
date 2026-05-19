package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.User;
import com.banditdev.eksamensprojekt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static com.banditdev.eksamensprojekt.model.UserExperience.SENIOR;
import static com.banditdev.eksamensprojekt.model.UserRole.MANAGER;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void validateUser_returnsTrueForCorrectInputs() {
        User u = new User(1, "Anders", "anders123", "pass1234", SENIOR, MANAGER);
        when(userRepository.findUserByUserUsername("anders123")).thenReturn(u);
        assertTrue(userService.validateUser("anders123", "pass1234"));
    }


    @Test
    void validateUser_returnsFalseWrongPassword() {
        User u = new User(1, "Anders", "anders123", "pass1234", SENIOR, MANAGER);
        when(userRepository.findUserByUserUsername("anders123")).thenReturn(u);
        assertFalse(userService.validateUser("anders123", "wrongpassword"));
    }

    @Test
    void validateUser_returnsFalseUnknownUser() {
        when(userRepository.findUserByUserUsername("ghost")).thenReturn(null);
        assertFalse(userService.validateUser("ghost", "anything"));
    }
    @Test
    void updateUserProfile_keepsOldPasswordWhenNewPasswordBlank() {
        User existing = new User(1, "Anders", "anders123", "oldpass", SENIOR, MANAGER);
        User formUser = new User(0, null, "newusername", "", SENIOR, MANAGER);
        when(userRepository.findUserByUserId(1)).thenReturn(existing);

        User result = userService.updateUserProfile(1, formUser);

        assertEquals("oldpass", result.getUserPassword());
        assertEquals("newusername", result.getUserUsername());
    }

    @Test
    void createUser_succeedsWhenUsernameIsAvailable() {
        User newUser = new User();
        newUser.setUserUsername("brandnew");
        when(userRepository.findUserByUserUsername("brandnew")).thenReturn(null);

        assertDoesNotThrow(() -> userService.createUser(newUser));
    }

    @Test
    void deleteUserById_succeedsWhenDeletingOtherUser() {
        assertDoesNotThrow(() -> userService.deleteUserById(2, 1));
        verify(userRepository).deleteUserByUserId(2);
    }

    @Test
    void updateUserAsAdmin_succeedsWhenUsernameIsOwnUsername() {
        User existing = new User(1, "Anders", "anders123", "pass1234", SENIOR, MANAGER);
        User updatedUser = new User(1, "Anders Updated", "anders123", "pass1234", SENIOR, MANAGER);
        when(userRepository.findUserByUserUsername("anders123")).thenReturn(existing);

        assertDoesNotThrow(() -> userService.updateUserAsAdmin(updatedUser));
        verify(userRepository).updateUserAsAdmin(updatedUser);
    }

}
