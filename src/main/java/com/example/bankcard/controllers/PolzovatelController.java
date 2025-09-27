package com.example.bankcard.controllers;

import com.example.bankcard.dto.CardDto;
import com.example.bankcard.dto.Response;
import com.example.bankcard.dto.TransactionDto;
import com.example.bankcard.dto.UserDto;
import com.example.bankcard.entity.Card;
import com.example.bankcard.exception.DeleteException;
import com.example.bankcard.services.CardCRUDService;
import com.example.bankcard.services.UserCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/polzovatel")
@RestController
@RequiredArgsConstructor
public class PolzovatelController {

    private final UserCRUDService userService;
    private final CardCRUDService cardService;


    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto newUserDto = userService.create(userDto);
        return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) throws DeleteException {
        try {
            if (userService.getById(id) != null) {
                return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
            }
        }catch (Exception e){
            throw new DeleteException("Пользователь " + id + " отсутствует");
        }
        return null;
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<Response> getBalance(@PathVariable Integer id) throws DeleteException{
        try {
            if (cardService.getById(id) != null) {
                Response response = new Response();
                response.setMessage("Баланс карты "+ id + " составляет: " + cardService.getById(id).getBalance());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch (Exception e){
            throw new DeleteException("Карта с id " + id + " не найдена");
        }
        return null;

    }

    @GetMapping("/callBlockCard/{id}")
    public ResponseEntity<Response> callBlockCard(@PathVariable Integer id) throws DeleteException{
        try {
            if (cardService.getById(id) != null){
                cardService.makeCallBlock(id,"BLOCK");
                Response response = new Response();
                response.setMessage("Запрос на блокировку карты " + id + " составлен");
                return new ResponseEntity<>(response,HttpStatus.OK);
            }

        }catch (Exception e){
            throw new DeleteException("Создание запроса на блокирование карты невозможно");
        }
        return null;
    }


    @PutMapping
    public ResponseEntity<Response> transaction(@RequestBody TransactionDto transactionDto) throws DeleteException {
        try {
            if (cardService.getById(transactionDto.getFromCardOneId()) != null && cardService.getById(transactionDto.getToCardTwoId()) != null
                    && transactionDto.getValue() > 0 && !cardService.getById(transactionDto.getFromCardOneId()).getStatus().equals(Card.StatusEnum.BLOCK)
                    && !cardService.getById(transactionDto.getToCardTwoId()).getStatus().equals(Card.StatusEnum.BLOCK)
                    && cardService.getById(transactionDto.getFromCardOneId()).getBalance() > transactionDto.getValue()) {

                cardService.transaction(transactionDto);
                Response response = new Response("Осуществлен перевод денежных средств с карты " + transactionDto.getFromCardOneId() +
                        " на карту " + transactionDto.getToCardTwoId() + " в размере: " + transactionDto.getValue());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch (Exception e){
            throw new DeleteException("Перевод денежных средств невозможен");
        }
        Response response = new Response("Перевод денежных средств невозможен");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
