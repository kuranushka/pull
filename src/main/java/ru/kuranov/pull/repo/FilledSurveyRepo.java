package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.model.entity.survey.FilledSurvey;

import java.util.List;

@Repository
public interface FilledSurveyRepo extends JpaRepository<FilledSurvey, Long> {

    List<FilledSurvey> findAllByInterviewerId(Long interviewerId);
}
