package com.example.bankcard.services;

import com.example.bankcard.dto.UserDto;
import com.example.bankcard.entity.User;
import com.example.bankcard.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserCRUDServiceTest {

    private final UserRepository userRepository =
            Mockito.mock(UserRepository.class);


    private final UserCRUDService userCRUDService =
            new UserCRUDService(userRepository);

    @Test
    @DisplayName("Test getById user")
    public void testGetById(){
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setCards(List.of());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = userCRUDService.getById(userId);
        assertEquals(userId, userDto.getId());
        verify(userRepository, times(1)).findById(userId);

    }

    @Test
    @DisplayName("Test getAll users")
    public void testGetAll(){
        List<User> users = new ArrayList<>();
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setCards(List.of());
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);
        Collection<UserDto> userDtos = userCRUDService.getAll();

        assertEquals(users.size(), userDtos.size());
        verify(userRepository, times(1)).findAll();
    }

    public void testBlockCard(){

    }

}
