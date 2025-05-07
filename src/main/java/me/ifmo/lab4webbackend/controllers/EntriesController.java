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
    ResponseEntity<?> getUserEntries(Principal principal) {
        User user = (User) this.userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(this.entryRepository.findByUser(user));
    }
}
