package org.flowers.project.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FlowerFilterBadRequestException extends ResponseStatusException {

    public FlowerFilterBadRequestException(String messageText) {
        super(HttpStatus.BAD_REQUEST, messageText);
    }

}