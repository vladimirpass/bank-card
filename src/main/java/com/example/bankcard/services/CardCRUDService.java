package com.example.bankcard.services;

import com.example.bankcard.dto.CardDto;
import com.example.bankcard.dto.TransactionDto;
import com.example.bankcard.entity.Card;
import com.example.bankcard.entity.User;
import com.example.bankcard.repositories.CardRepository;
import com.example.bankcard.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class CardCRUDService implements CRUDServiceCard<CardDto>{

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    private final HashMap<Integer, String> listCalls = new HashMap<>();


    @Override
    public CardDto getById(Integer id) {
        log.info("Get by id " + id);
        Card card = cardRepository.findById(id).orElseThrow();
        return mapToDto(card);
    }

    @Override
    public Collection<CardDto> getAll() {
        log.info("Get all");
        return cardRepository.findAll()
                .stream()
                .map(CardCRUDService::mapToDto)
                .toList();
    }

    @Override
    public CardDto create(CardDto cardDto) {
        log.info("Create");
        Card card = mapToEntity(cardDto);
        Integer userId = cardDto.getUserId();
        User user = userRepository.findById(userId).orElseThrow();
        card.setUsers(user);
        card.setName(cardDto.getName());
        card.setNumberCard(randomNumberCard());
        card.setTimeWork(randomTimeWork());
        card.setStatus(Card.StatusEnum.ACTIVE);

        if(cardDto.getName().equals(user.getNameUser()) && cardDto.getUserId().equals(user.getId())){
            cardRepository.save(card);

            return mapToDto(card);
        }
        return null;
    }

    @Override
    public CardDto update(Integer id, CardDto cardDto) {
        log.info("Update");
        cardDto.setId(id);
        Card card = mapToEntity(cardDto);
        Integer userId = cardDto.getUserId();
        User user = userRepository.findById(userId).orElseThrow();
        card.setUsers(user);
        card.setName(cardDto.getName());

        if(cardDto.getName().equals(user.getNameUser()) && cardDto.getUserId().equals(user.getId())){
            cardRepository.save(card);

            return mapToDto(card);
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Delete " + id);
        cardRepository.deleteById(id);

    }

    @Override
    public CardDto blockCard(Integer id){
        log.info("Block Card " + id);
        if(listCalls.get(id).equals("BLOCK")) {
            Card card = cardRepository.findById(id).orElseThrow();
            card.setStatus(Card.StatusEnum.BLOCK);
            cardRepository.save(card);

            return mapToDto(card);
        }
        return null;
    }

    @Override
    public Double getBalanceCard(Integer id){
        Card card = cardRepository.findById(id).orElseThrow();

        return card.getBalance();
    }

    @Override
    public void makeCallBlock(Integer id, String callBlock){
        listCalls.put(id,callBlock);
        String str = "Запрос на блокировку карты " + id + " составлен";
        System.out.println(str + " количество запросов " + listCalls.size());

    }

    @Override
    public void transaction(TransactionDto transactionDto){
        Card card = cardRepository.findById(transactionDto.getFromCardOneId()).orElseThrow();
        Card card2 = cardRepository.findById(transactionDto.getToCardTwoId()).orElseThrow();

        if(!card.getStatus().equals(Card.StatusEnum.BLOCK)
                && !card2.getStatus().equals(Card.StatusEnum.BLOCK)){

            BigDecimal cardBalanceOne = new BigDecimal("" + card.getBalance());
            BigDecimal cardBalanceTwo = new BigDecimal("" + card2.getBalance());
            BigDecimal value = new BigDecimal("" + transactionDto.getValue());

            card.setBalance(cardBalanceOne.subtract(value).doubleValue());
            card2.setBalance(cardBalanceTwo.add(value).doubleValue());

            cardRepository.save(card);
            cardRepository.save(card2);
        }

    }


    public static CardDto mapToDto(Card card){
        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setName(card.getName());
        cardDto.setNumberCard(card.getNumberCard());
        cardDto.setTimeWork(card.getTimeWork());
        cardDto.setStatus(card.getStatus());
        cardDto.setBalance(card.getBalance());
        cardDto.setUserId(card.getUsers().getId());

        return cardDto;
    }

    public static Card mapToEntity(CardDto cardDto){
        Card card = new Card();
        card.setId(cardDto.getId());
        card.setName(cardDto.getName());
        card.setNumberCard(cardDto.getNumberCard());
        card.setTimeWork(cardDto.getTimeWork());
        card.setStatus(cardDto.getStatus());
        card.setBalance(cardDto.getBalance());

        return card;
    }


    public long randomNumberCard(){
        long minValue = 1000000000000001L;
        long maxValue = 9999999999999999L;

        return ThreadLocalRandom.current().nextLong(minValue,maxValue + 1);
    }

    public Integer randomTimeWork(){
        int minValue = 1;
        int maxValue = 10;

        return minValue + (int)(Math.random() * (maxValue - minValue + 1));
    }

}
