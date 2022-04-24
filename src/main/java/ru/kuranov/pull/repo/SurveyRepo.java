package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.model.entity.survey.Survey;

@Repository
public interface SurveyRepo extends JpaRepository<Survey, Long> {

}
