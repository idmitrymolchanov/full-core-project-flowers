package org.flowers.project.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BaseResponse implements Serializable {

    private static final long serialVersionUid = 12345L;

    @Schema(description = "идентификатор записи")
    private UUID id;

}