package ru.kuranov.pull.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kuranov.pull.dto.PullDto;
import ru.kuranov.pull.entity.Pull;
import ru.kuranov.pull.mapper.PullMapper;
import ru.kuranov.pull.repo.ItemRepo;
import ru.kuranov.pull.repo.PullRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequestMapping
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

    public boolean update(PullDto pullDto) {
        //TODO дописать метод
        return true;
    }
}
