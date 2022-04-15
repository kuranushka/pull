package ru.kuranov.pull.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.dto.PullDto;
import ru.kuranov.pull.service.FillPullService;
import ru.kuranov.pull.service.PullService;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pulls")
@RequiredArgsConstructor
public class PullController {

    private final PullService pullService;
    private final FillPullService fillPullService;

    @GetMapping
    public List<PullDto> getAllPull(Principal principal) {
        if (principal == null) {
            return pullService.findAllByIsActive();
        }
        return pullService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPull(Principal principal,
                                     @PathVariable("id") Long id) {
        PullDto pullDto = pullService.findById(id);
        if (principal == null && !pullDto.getIsActive()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pullDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPull(@Validated @RequestBody PullDto pullDto) {
        PullDto savedPullDto = pullService.save(pullDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/pulls/" + savedPullDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePull(@Validated @RequestBody PullDto pullDto,
                                        @PathVariable("id") Long id) {
        if (pullService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pullDto.setId(id);
        pullService.update(pullDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePull(@PathVariable("id") Long id) {
        pullService.deletePull(id);
    }

    //TODO @Validated
    @PutMapping("/fill/{pullSourceId}")
    public ResponseEntity<?> saveFilledPull(@Validated @RequestBody FillPullDto fillPullDto,
                                            @PathVariable("pullSourceId") Long pullSourceId,
                                            @RequestParam(name = "interviewerId") Long interviewerId) {
        if (pullService.isActivePull(pullSourceId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!fillPullService.isValidFillPull(fillPullDto, pullSourceId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        fillPullService.saveFilledPull(fillPullDto, pullSourceId, interviewerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/interviewer/{interviewerId}/fill-pull")
    public ResponseEntity<List<FillPullDto>> getAllFilledPull(@PathVariable("interviewerId") Long interviewerId) {
        List<FillPullDto> fillPullDtoList = fillPullService.findAllByInterviewerId(interviewerId);
        if (fillPullDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fillPullDtoList, HttpStatus.OK);
        //TODO rewrite
    }
}
