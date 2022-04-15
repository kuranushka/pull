package ru.kuranov.pull.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.entity.FillPull;

import java.util.List;

@Repository
public interface FillPullRepo extends JpaRepository<FillPull, Long> {

    List<FillPullDto> findAllByInterviewerId(Long interviewerId);
}
