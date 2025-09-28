package com.example.bankcard.services;

import com.example.bankcard.dto.CardDto;
import com.example.bankcard.entity.Card;
import com.example.bankcard.entity.User;
import com.example.bankcard.repositories.CardRepository;
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


public class CardCRUDServiceTest {

    private final CardRepository cardRepository =
            Mockito.mock(CardRepository.class);

    private final UserRepository userRepository =
            Mockito.mock(UserRepository.class);

    private final CardCRUDService cardCRUDService =
            new CardCRUDService(cardRepository,userRepository);

    private final UserCRUDService userCRUDService =
            new UserCRUDService(userRepository);


    @Test
    @DisplayName("Test getById card")
    public void testGetById(){
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setCards(List.of());

        int cardId = 1;
        Card card = new Card();
        card.setId(cardId);
        card.setName("Petia");
        card.setNumberCard(2121245331958183L);
        card.setTimeWork(3);
        card.setStatus(Card.StatusEnum.ACTIVE);
        card.setBalance(250);
        card.setUsers(user);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        CardDto cardDto = cardCRUDService.getById(cardId);

        assertEquals(cardId, cardDto.getId());
        verify(cardRepository, times(1)).findById(userId);

    }

    @Test
    @DisplayName("Test getAll cards")
    public void testGetAll(){
        List<Card> cards = new ArrayList<>();
        int userId = 1;
        User user = new User();
        user.setId(userId);
        user.setCards(List.of());

        int cardId = 1;
        Card card = new Card();
        card.setId(cardId);
        card.setName("Petia");
        card.setNumberCard(2121245331958183L);
        card.setTimeWork(3);
        card.setStatus(Card.StatusEnum.ACTIVE);
        card.setBalance(250);
        card.setUsers(user);

        cards.add(card);

        when(cardRepository.findAll()).thenReturn(cards);
        Collection<CardDto> cardDtos = cardCRUDService.getAll();

        assertEquals(cards.size(), cardDtos.size());
        verify(cardRepository, times(1)).findAll();
    }

}
