package me.ifmo.lab4webbackend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entries")
public class EntriesController {
  @Autowired
    private UserService userService;

  @Autowired
  private EntryRepository entryRepository;

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
