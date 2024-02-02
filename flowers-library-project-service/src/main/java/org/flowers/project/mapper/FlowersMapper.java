package org.flowers.project.mapper;

import org.flowers.project.dto.v1.Flower;
import org.flowers.project.entity.FlowerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlowersMapper {

    FlowerEntity toFlowerEntity(Flower flower);

    Flower toFlower(FlowerEntity flowerEntity);

    List<Flower> toFlowers(List<FlowerEntity> flowerEntity);

}