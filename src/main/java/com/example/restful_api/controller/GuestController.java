package com.example.restful_api.controller;

import com.example.restful_api.model.Guest;
import com.example.restful_api.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.naming.NameNotFoundException;

@RestController 
@RequestMapping("/guests")
public class GuestController {



    @Autowired
    private GuestRepository guestRepository;

    //Get all guests
    @GetMapping()
    public List<Guest> getAllGuests()
    {
        return guestRepository.findAll();
    }
    
    //Get a guest by ID
    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable int id){
        return guestRepository.findById(id).orElseThrow(() -> new GuestNotFoundException(id));
    }

    //Create a new guest 
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Guest createGuest(@RequestBody Guest guest){
        return guestRepository.save(guest);
    }
    
    //Update an existing guest by ID
    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable int id, @RequestBody Guest updatedGuest){
        return guestRepository.findById(id).map(guest -> {
            guest.setName(updatedGuest.getName());
            guest.setEmail(updatedGuest.getEmail());
            return guestRepository.save(guest);
        }).orElseThrow(() -> new GuestNotFoundException(id));
    }

    //Delete a guest by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuest(@PathVariable int id) {
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new GuestNotFoundException(id));
        guestRepository.delete(guest);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class GuestNotFoundException extends RuntimeException {
        public GuestNotFoundException(int id) {
            super("Could not find guest with id: " + id);
        }
}
}
