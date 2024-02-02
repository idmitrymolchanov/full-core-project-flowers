package org.flowers.project.controller;

import lombok.RequiredArgsConstructor;
import org.flowers.project.api.v1.FlowersApiV1;
import org.flowers.project.dto.v1.*;
import org.flowers.project.service.FlowersFilterService;
import org.flowers.project.service.FlowersService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FlowersController implements FlowersApiV1 {

    private final FlowersService service;
    private final FlowersFilterService filterService;

    @Override
    public BaseResponse createFlower(Flower flower) {
        return service.createFlower(flower);
    }

    @Override
    public Flower getFlowerById(UUID id) {
        return service.getFlowerById(id);
    }

    @Override
    public void patchFlower(UUID id, PatchFlower patchFlower) {
        service.patchFlowers(id, patchFlower);
    }

    @Override
    public List<Flower> getFlowers(String type, String color) {
        return service.getFlowersByTypeAndColor(type, color);
    }

    @Override
    public FilterResponse getFlowersPost(FilterRequest request, String userId) {
        return filterService.getFilterResponse(request);
    }

}