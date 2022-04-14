package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.entity.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {
}
