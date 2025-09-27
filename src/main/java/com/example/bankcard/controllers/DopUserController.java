package com.example.bankcard.controllers;

import com.example.bankcard.dto.Response;
import com.example.bankcard.dto.UserDto;
import com.example.bankcard.exception.DeleteException;
import com.example.bankcard.services.UserCRUDService;
import jakarta.websocket.DecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/USERS")
@RestController
@RequiredArgsConstructor
public class DopUserController {

    private final UserCRUDService userService;

    @GetMapping
    public Collection<UserDto> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) throws DeleteException{
        try {
            if (userService.getById(id) != null) {
                return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
            }
        }catch (Exception e){
            throw new DeleteException("Категория с ID " + id + " не найдена");
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto newUserDto = userService.create(userDto);
        return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto userDto){
        userDto.setId(id);
        UserDto updateUserDto = userService.update(id, userDto);
        return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Integer id) throws DeleteException{
        try {
            if(userService.getById(id) != null){
                userService.delete(id);
                Response response = new Response("Delete User");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }catch(Exception e){
            throw new DeleteException("Категория с ID " + id + " не найдена");
        }
        return null;
    }




}
