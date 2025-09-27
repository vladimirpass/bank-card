package com.example.bankcard.controllers;

import com.example.bankcard.dto.CardDto;
import com.example.bankcard.dto.Response;
import com.example.bankcard.exception.DeleteException;
import com.example.bankcard.services.CardCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/card")
public class DopCardController {

    private final CardCRUDService cardService;


    public DopCardController(CardCRUDService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable Integer id) throws DeleteException{
        try {
            if (cardService.getById(id) != null) {
                return new ResponseEntity<>(cardService.getById(id), HttpStatus.OK);
            }
        }catch (Exception e){
            throw new DeleteException("Карта с id " + id + " не найдена");
        }
        return null;
    }

    @GetMapping
    public Collection<CardDto> getAllCards(){
        return cardService.getAll();
    }

    @PostMapping
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto){
        CardDto newCard = cardService.create(cardDto);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(@PathVariable Integer id,@RequestBody CardDto cardDto){
        CardDto updateCardDto = cardService.update(id,cardDto);
        cardDto.setId(id);
        return new ResponseEntity<>(cardDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCard(@PathVariable Integer id) throws DeleteException{
        try {
            if (cardService.getById(id) != null) {
                cardService.deleteById(id);
                Response response = new Response("Card delete");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch(Exception e){
            throw new DeleteException("Карта с ID " + id + " не найдена");
        }
        return null;
    }
}
