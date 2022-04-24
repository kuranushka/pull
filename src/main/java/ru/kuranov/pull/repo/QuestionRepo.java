package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.model.entity.question.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
}
