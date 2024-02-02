package org.flowers.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class FlowerNotFoundEntity extends ResponseStatusException {

    public FlowerNotFoundEntity(UUID id) {
        super(HttpStatus.NOT_FOUND, String.format("not found entity with id: %s", id));
    }

}