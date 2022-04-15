package ru.kuranov.pull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kuranov.pull.dto.PullDto;
import ru.kuranov.pull.entity.Pull;
import ru.kuranov.pull.mapper.PullMapper;
import ru.kuranov.pull.repo.ItemRepo;
import ru.kuranov.pull.repo.PullRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PullService {

    private final ItemRepo itemRepo;
    private final PullRepo pullRepo;
    private final PullMapper pullMapper;

    public List<PullDto> findAll() {
        return pullRepo.findAll().stream()
                .map(pullMapper::getPullDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PullDto save(PullDto pullDto) {
        itemRepo.saveAll(pullDto.getItems());
        Pull pull = pullMapper.getPull(pullDto);
        pullRepo.save(pull);
        return pullMapper.getPullDto(pull);

    }

    public List<PullDto> findAllByIsActive() {
        return pullRepo.findAllByIsActive(true).stream()
                .map(pullMapper::getPullDto)
                .collect(Collectors.toList());
    }

    public PullDto findById(Long id) {
        Optional<Pull> optionalPull = pullRepo.findById(id);
        return pullMapper.getPullDto(optionalPull.orElseThrow());
    }

    @Transactional
    public void update(PullDto pullDto) {
        Optional<Pull> optionalPull = pullRepo.findById(pullDto.getId());
        LocalDate beginDale = optionalPull.get().getBeginDate();
        pullDto.setBeginDate(beginDale);
        itemRepo.saveAll(pullDto.getItems());
        pullRepo.save(pullMapper.getPull(pullDto));
    }

    public void deletePull(Long id) {
        Pull pull = pullRepo.findById(id).orElseThrow();
        pullRepo.delete(pull);
    }

    public boolean isActivePull(Long id) {
        Optional<Pull> optionalPull = pullRepo.findById(id);
        return optionalPull.orElseThrow().getIsActive();
    }
}
