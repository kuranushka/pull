package ru.kuranov.pull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.entity.fill.FillItem;
import ru.kuranov.pull.entity.fill.FillPull;
import ru.kuranov.pull.entity.main.Pull;
import ru.kuranov.pull.entity.type.Type;
import ru.kuranov.pull.exception.NumberOfItemsInPullDoesNotMatchException;
import ru.kuranov.pull.exception.SingleOptionContainsMoreThanOneTrueAnswerException;
import ru.kuranov.pull.mapper.FillPullMapper;
import ru.kuranov.pull.repo.FillItemRepo;
import ru.kuranov.pull.repo.FillPullRepo;
import ru.kuranov.pull.repo.PullRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillPullService {

    private final FillPullRepo fillPullRepo;
    private final FillPullMapper fillPullMapper;
    private final PullRepo pullRepo;
    private final FillItemRepo fillItemRepo;


    public boolean isValidFillPull(FillPullDto fillPullDto, Long pullSourceId) {

        Pull pull = pullRepo.findById(pullSourceId).orElseThrow();

        int pullItemsSize = pull.getItems().size();
        int fillPullItemsSize = fillPullDto.getFillItems().size();
        boolean isEqualsItemCount = pullItemsSize == fillPullItemsSize;
        if (!isEqualsItemCount) {
            throw new NumberOfItemsInPullDoesNotMatchException();
        }
        for (FillItem fillItem : fillPullDto.getFillItems()) {
            if (fillItem.getType().equals(Type.SINGLE_OPTION)) {
                List<Boolean> listValues = fillItem.getAnswer().values().stream()
                        .filter(value -> value.equals(true))
                        .collect(Collectors.toList());
                if (listValues.size() > 1) {
                    throw new SingleOptionContainsMoreThanOneTrueAnswerException();
                }
            }
        }
        return pullItemsSize == fillPullItemsSize;
    }

    public void saveFilledPull(FillPullDto fillPullDto, Long pullSourceId, Long interviewerId) {
        FillPull fillPull = fillPullMapper.getFillPull(fillPullDto);
        fillPull.setSourcePullId(pullSourceId);
        fillPull.setInterviewerId(interviewerId);
        fillItemRepo.saveAll(fillPull.getFillItems());
        fillPullRepo.save(fillPull);
    }


    public List<FillPullDto> findAllByInterviewerId(Long interviewerId) {
        return fillPullRepo.findAllByInterviewerId(interviewerId).stream()
                .map(fillPullMapper::getFillPull)
                .collect(Collectors.toList());
    }
}
