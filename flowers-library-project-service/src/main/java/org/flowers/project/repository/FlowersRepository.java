package org.flowers.project.repository;

import org.flowers.project.entity.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlowersRepository extends JpaRepository<FlowerEntity, UUID> {

    List<FlowerEntity> findAllByTypeAndColor(String type, String color);

}