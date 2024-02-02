package org.flowers.project.service;

import org.flowers.project.dto.v1.BaseResponse;
import org.flowers.project.dto.v1.Flower;
import org.flowers.project.dto.v1.PatchFlower;
import org.flowers.project.entity.FlowerEntity;
import org.flowers.project.exception.FlowerNotFoundEntity;
import org.flowers.project.mapper.FlowersMapper;
import org.flowers.project.repository.FlowersRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowersService {

    private final FlowersMapper mapper;
    private final FlowersRepository repository;

    public BaseResponse createFlower(Flower flower) {
        var flowerEntity = mapper.toFlowerEntity(flower);
        return new BaseResponse(repository.save(flowerEntity).getId());
    }

    public Flower getFlowerById(UUID id) {
        return mapper.toFlower(getFlowerEntity(id));
    }

    public void patchFlowers(UUID id, PatchFlower patchFlower) {
        var flowerEntity = getFlowerEntity(id);
        Optional.ofNullable(patchFlower.getShortName()).ifPresent(flowerEntity::setShortName);
        Optional.ofNullable(patchFlower.getFullName()).ifPresent(flowerEntity::setFullName);
        Optional.ofNullable(patchFlower.getColor()).ifPresent(flowerEntity::setColor);
        Optional.ofNullable(patchFlower.getType()).ifPresent(flowerEntity::setType);
        Optional.ofNullable(patchFlower.getDescription()).ifPresent(flowerEntity::setDescription);
        repository.saveAndFlush(flowerEntity);
    }

    public List<Flower> getFlowersByTypeAndColor(String type, String color) {
        var flowers = repository.findAllByTypeAndColor(type, color);
        return mapper.toFlowers(flowers);
    }

    private FlowerEntity getFlowerEntity(UUID id) {
        return repository.findById(id).orElseThrow(() -> new FlowerNotFoundEntity(id));
    }

}