package org.flowers.project.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Validated
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flower implements Serializable {

    private static final long serialVersionUID = 12345L;

    @Schema(required = true, accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор обращения")
    private UUID id;

    @Schema(required = true, description = "краткое наименование")
    private String shortName;
    @Schema(description = "полное наименование")
    private String fullName;
    @Schema(description = "цвет")
    private String color;
    @Schema(description = "тип")
    private String type;
    @Schema(description = "дополнительное описание")
    private String description;

    @Schema(required = true, accessMode = Schema.AccessMode.READ_ONLY, description = "Дата и время создания")
    private LocalDateTime createDate;
    @Schema(required = true, accessMode = Schema.AccessMode.READ_ONLY, description = "Дата и время последнего изменения")
    private LocalDateTime lastUpdateDate;

}