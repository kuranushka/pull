package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.entity.fill.FillItem;

@Repository
public interface FillItemRepo extends JpaRepository<FillItem, Long> {
}
