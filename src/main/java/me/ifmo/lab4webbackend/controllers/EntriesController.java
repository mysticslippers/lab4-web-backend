package me.ifmo.lab4webbackend.controllers;

import me.ifmo.lab4webbackend.DTO.EntryDTO;
import me.ifmo.lab4webbackend.entities.Entry;
import me.ifmo.lab4webbackend.entities.User;
import me.ifmo.lab4webbackend.repositories.EntryRepository;
import me.ifmo.lab4webbackend.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entries")
public class EntriesController {
  @Autowired
    private UserService userService;

  @Autowired
  private EntryRepository entryRepository;

  private User getCurrentUser(Principal principal) {
      return (User) this.userService.loadUserByUsername(principal.getName());
  }
  
  @GetMapping
  public ResponseEntity<?> getUserEntries(Principal principal) {
      User user = (User) this.userService.loadUserByUsername(principal.getName());
      return ResponseEntity.ok(this.entryRepository.findByUser(user));
  }

  @PostMapping
  public ResponseEntity<?> addEntry(@Validated @RequestBody EntryDTO entryDTO, Principal principal) {
      User user = (User) this.userService.loadUserByUsername(principal.getName());
      return ResponseEntity.ok(this.entryRepository.save(new Entry(
              entryDTO.getX(),
              entryDTO.getY(),
              entryDTO.getR(),
              user)));
  }

  @DeleteMapping
  public ResponseEntity<?> deleteUserEntries(Principal principal) {
      User user = (User) this.userService.loadUserByUsername(principal.getName());
      return ResponseEntity.ok(this.entryRepository.deleteByUser(user));
  }
}
