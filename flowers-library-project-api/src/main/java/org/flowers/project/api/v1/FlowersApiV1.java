package org.flowers.project.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.flowers.project.dto.v1.*;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@Tag(name = "Обращения", description = "Методы для управления записями об обращениях в Колл Центр")
//@FeignClient(name = "FlowersService", url = "${org.api.flower-service.feign.url}")
public interface FlowersApiV1 {

    @Operation(summary = "Создать растение")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/org/api/v1/flower", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    BaseResponse createFlower(@RequestBody @Valid Flower flower);

    @Operation(summary = "Получить параметры растения")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/org/api/v1/flower/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    Flower getFlowerById(@Parameter(in = ParameterIn.PATH) @PathVariable UUID id);

    @Operation(summary = "Обновить параметры растения")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(path = "/org/api/v1/flower/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    void patchFlower(
            @PathVariable UUID id,
            @RequestBody PatchFlower patchFlower
    );

    @Operation(summary = "Получить список растений")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/org/api/v1/flowers", produces = {MediaType.APPLICATION_JSON_VALUE})
    List<Flower> getFlowers(
            @Parameter(in = ParameterIn.QUERY, description = "Тип растения") @RequestParam(required = false) String type,
            @Parameter(in = ParameterIn.QUERY, description = "Цвет растения") @RequestParam(required = false) @Valid String color
    );

    @Operation(summary = "Получить список растений по расширенным фильтрам")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/org/api/v1/flowers", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    FilterResponse getFlowersPost(
            @RequestBody @Valid FilterRequest request,
            @RequestHeader(value = "X-HTTP-USER-ID", required = false) @Parameter(in = ParameterIn.HEADER, name = "X-HTTP-USER-ID") String userId
    );

}