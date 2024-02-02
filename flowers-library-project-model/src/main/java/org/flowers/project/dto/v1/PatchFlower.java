package org.flowers.project.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class PatchFlower implements Serializable {

    private static final long serialVersionUID = 12345L;

    @Schema(description = "краткое наименование")
    private String shortName;
    @Schema(description = "полное наименование")
    private String fullName;
    @Schema(description = "цвет")
    private String color;
    @Schema(description = "тип")
    private String type;
    @Schema(description = "дополнительное описание")
    private String description;

}