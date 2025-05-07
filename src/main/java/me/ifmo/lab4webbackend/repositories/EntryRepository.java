package me.ifmo.lab4webbackend.repositories;

import jakarta.transaction.Transactional;
import me.ifmo.lab4webbackend.entities.Entry;
import me.ifmo.lab4webbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByUser(User user);

    @Transactional
    long deleteByUser(User user);
}
