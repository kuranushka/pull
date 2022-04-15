package ru.kuranov.pull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.entity.FillPull;
import ru.kuranov.pull.mapper.FillPullMapper;
import ru.kuranov.pull.repo.FillPullRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FillPullService {

    private final FillPullRepo fillPullRepo;
    private final FillPullMapper fillPullMapper;


    public void saveFilledPull(FillPullDto fillPullDto, Long pullSourceId, Long interviewerId) {
        FillPull fillPull = fillPullMapper.getFillPull(fillPullDto);
        fillPull.setInterviewerId(interviewerId);
        fillPull.setPullSourceId(pullSourceId);
        fillPullRepo.save(fillPull);
    }

    public List<FillPullDto> findAllByInterviewerId(Long interviewerId) {
        return fillPullRepo.findAllByInterviewerId(interviewerId);
    }
}
