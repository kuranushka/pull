package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.entity.Pull;

import java.util.List;

@Repository
public interface PullRepo extends JpaRepository<Pull, Long> {

    List<Pull> findAllByIsActive(Boolean isActive);
}
