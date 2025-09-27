package com.example.bankcard.controllers;


import com.example.bankcard.dto.CardDto;
import com.example.bankcard.dto.Response;
import com.example.bankcard.services.CardCRUDService;
import com.example.bankcard.services.UserCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/administrator")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserCRUDService userService;
    private final CardCRUDService cardService;


    @PostMapping
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto){
        CardDto newCard = cardService.create(cardDto);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @GetMapping
    public Collection<CardDto> getAllCards(){
        return cardService.getAll();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> blockCard(@PathVariable Integer id){
        if (cardService.getListCalls().get(id) != null
                && cardService.getListCalls().get(id).equals("BLOCK")) {
            cardService.blockCard(id);
            Response response = new Response("Карта " + id + " была заблокирована");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        Response response = new Response("Запрос на блокирование карты " + id + " не был составлен пользователем");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
