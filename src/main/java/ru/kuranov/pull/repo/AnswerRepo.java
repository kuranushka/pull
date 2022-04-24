package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.model.entity.answer.Answer;

@Repository
public interface AnswerRepo extends JpaRepository<Answer, Long> {
}
