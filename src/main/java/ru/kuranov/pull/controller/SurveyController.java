package ru.kuranov.pull.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kuranov.pull.error.exception.SurveyWithThisIdIsNotActiveException;
import ru.kuranov.pull.error.handler.ErrorHandler;
import ru.kuranov.pull.model.dto.FilledSurveyDto;
import ru.kuranov.pull.model.dto.SurveyDto;
import ru.kuranov.pull.service.FillSurveyService;
import ru.kuranov.pull.service.SurveyService;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@Tag(name = "Survey Controller", description = "Операции с опросами")
@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final FillSurveyService fillSurveyService;
    private final ErrorHandler errorHandler;


    @Operation(summary = "Получить все опросы", description = "Не авторизованный пользователь получает только активные опросы. Администратор получит весь список")
    @GetMapping
    public Collection<SurveyDto> getAllSurvey(Principal principal) {
        if (principal == null) {
            return surveyService.findAllByIsActive();
        }
        return surveyService.findAll();
    }


    @Operation(summary = "Получение одного опроса по id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getSurvey(Principal principal,
                                       @PathVariable @Parameter(description = "id опроса") Long id) {
        SurveyDto surveyDto = surveyService.findById(id);
        if (principal == null) {
            if (!surveyDto.getIsActive()) {
                throw new SurveyWithThisIdIsNotActiveException();
            } else {
                return new ResponseEntity<>(surveyDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(surveyDto, HttpStatus.OK);
    }


    @Operation(summary = "Создание нового опроса", description = "Метод доступен Администратору системы")
    @PostMapping
    public ResponseEntity<?> createSurvey(@Validated @RequestBody SurveyDto surveyDto,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            errorHandler.handle(bindingResult);
        }
        surveyService.checkValidDate(surveyDto);
        surveyService.checkValidQuestions(surveyDto.getQuestions());
        SurveyDto savedSurveyDto = surveyService.save(surveyDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/surveys/" + savedSurveyDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }


    @Operation(summary = "Изменение существующего опроса с идентификатором id", description = "Метод доступен Администратору системы")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSurvey(@Validated @RequestBody SurveyDto surveyDto,
                                          @PathVariable @Parameter(description = "id опроса") Long id,
                                          BindingResult bindingResult) {
        if (surveyService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            errorHandler.handle(bindingResult);
        }
        surveyService.checkValidDate(surveyDto);
        surveyService.checkValidQuestions(surveyDto.getQuestions());

        surveyDto.setId(id);
        surveyService.update(surveyDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Удаление существующего опроса с идентификатором id", description = "Метод доступен Администратору системы")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSurvey(@PathVariable @Parameter(description = "id опроса") Long id) {
        surveyService.deleteSurvey(id);
    }


    @Operation(summary = "Заполнение пользователем существующего. активного опроса с идентификатором surveySourceId", description = "В параметрах необходимо передать идентификатор пользователя interviewerId")
    @PutMapping("/fill/{surveySourceId}")
    public ResponseEntity<?> fillSurvey(@RequestBody FilledSurveyDto filledSurveyDto,
                                        @PathVariable @Parameter(description = "id опроса") Long surveySourceId,
                                        @RequestParam @Parameter(description = "id пользователя") Long interviewerId) {

        if (!surveyService.isActiveSurvey(surveySourceId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        fillSurveyService.checkValidFilledSurvey(filledSurveyDto, surveySourceId);

        fillSurveyService.saveFilledSurvey(filledSurveyDto, surveySourceId, interviewerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Получение списка опросов пользовател")
    @GetMapping("/interviewer/{interviewerId}/fill-survey")
    public ResponseEntity<Collection<FilledSurveyDto>> getAllFilledSurvey(@PathVariable @Parameter(description = "id пользователя") Long interviewerId) {

        Collection<FilledSurveyDto> filledSurveyDtoList = fillSurveyService.findAllByInterviewerId(interviewerId);

        if (filledSurveyDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(filledSurveyDtoList, HttpStatus.OK);
    }
}
