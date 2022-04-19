package ru.kuranov.pull.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kuranov.pull.dto.FillPullDto;
import ru.kuranov.pull.dto.PullDto;
import ru.kuranov.pull.exception.PollWithThisIdIsNotActiveException;
import ru.kuranov.pull.service.FillPullService;
import ru.kuranov.pull.service.PullService;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pulls")
@RequiredArgsConstructor
public class PullController {

    private final PullService pullService;
    private final FillPullService fillPullService;

    @Operation(description = "Полуение списка всех опросов. Не авторизованный пользователь получает только активные опросы. Администратор весть список")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех опросов", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PullDto.class))})})
    @GetMapping
    public Collection<PullDto> getAllPull(Principal principal) {
        if (principal == null) {
            return pullService.findAllByIsActive();
        }
        return pullService.findAll();
    }

    @Operation(description = "Получение одного опроса с идентификатором :id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Найденный опрос", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PullDto.class))}),
            @ApiResponse(responseCode = "204", description = "Данный опрос не активен", content = @Content),
            @ApiResponse(responseCode = "404", description = "Опрос не найден", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<?> getPull(Principal principal,
                                     @PathVariable("id") Long id) {
        PullDto pullDto = pullService.findById(id);
        if (principal == null) {
            if (!pullDto.getIsActive()) {
                throw new PollWithThisIdIsNotActiveException();
            } else {
                return new ResponseEntity<>(pullDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(pullDto, HttpStatus.OK);
    }

    @Operation(description = "Создание нового опроса метод доступен Администратору системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создан новый опрос", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "409", description = "Опрос не создан, request не соответсвует формату", content = @Content)})
    @PostMapping
    public ResponseEntity<?> createPull(@Validated @RequestBody PullDto pullDto) {
        if (!pullService.isValidItem(pullDto.getItems())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PullDto savedPullDto = pullService.save(pullDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/pulls/" + savedPullDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @Operation(description = "Изменение существующего опроса с идентификатором :id, метод доступен Администратору системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Опрос изменен", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request не соответсвует формату", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Опрос не найден", content = @Content),
            @ApiResponse(responseCode = "409", description = "Опрос не изменен, request не соответсвует формату", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePull(@Validated @RequestBody PullDto pullDto,
                                        @PathVariable("id") Long id) {
        if (pullService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (pullDto.getItems() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        pullDto.setId(id);
        pullService.update(pullDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Удаление существующего опроса с идентификатором :id, метод доступен Администратору системы")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Опрос удален", content = @Content),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован", content = @Content),
            @ApiResponse(responseCode = "404", description = "Опрос не найден", content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePull(@PathVariable("id") Long id) {
        pullService.deletePull(id);
    }


    @Operation(description = "Заполнение пользователем существующего. активного опроса с идентификатором :pullSourceId, также в параметрах необходимо передать идентификатор пользователя :interviewerId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Опрос записан", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request не соответсвует формату", content = @Content),
            @ApiResponse(responseCode = "404", description = "Опрос не найден", content = @Content),
            @ApiResponse(responseCode = "409", description = "Опрос не изменен, request не соответсвует формату", content = @Content)})
    @PutMapping("/fill/{pullSourceId}")
    public ResponseEntity<?> fillPull(@RequestBody FillPullDto fillPullDto,
                                      @PathVariable("pullSourceId") Long pullSourceId,
                                      @RequestParam(name = "interviewerId") Long interviewerId) {
        if (!pullService.isActivePull(pullSourceId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!fillPullService.isValidFillPull(fillPullDto, pullSourceId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        fillPullService.saveFilledPull(fillPullDto, pullSourceId, interviewerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Получение списка опросов пользовател :interviewerId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех опросов пользователя", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FillPullDto.class))})})
    @GetMapping("/interviewer/{interviewerId}/fill-pull")
    public ResponseEntity<Collection<FillPullDto>> getAllFilledPull(@PathVariable("interviewerId") Long interviewerId) {
        List<FillPullDto> fillPullDtoList = fillPullService.findAllByInterviewerId(interviewerId);
        if (fillPullDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fillPullDtoList, HttpStatus.OK);
    }
}
