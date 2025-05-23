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
  public ResponseEntity<Entry> addEntry(@Validated @RequestBody EntryDTO entryDTO, Principal principal) {
      User user = getCurrentUser(principal);
      Entry entry = new Entry(entryDTO.getX(), entryDTO.getY(), entryDTO.getR(), user);
      Entry savedEntry = this.entryRepository.save(entry);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteUserEntries(Principal principal) {
      User user = getCurrentUser(principal);
      this.entryRepository.deleteByUser(user);
      return ResponseEntity.noContent().build();
  }
}
