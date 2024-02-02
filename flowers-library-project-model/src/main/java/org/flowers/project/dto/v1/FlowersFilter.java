package org.flowers.project.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Schema(description = "Параметры фильтрации списка обращений")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlowersFilter implements Serializable {

    private static final long serialVersionUID = 12345L;

    @Schema(description = "краткое наименование")
    private String shortName;

    @Schema(description = "полное наименование")
    private String fullName;

    @Schema(description = "цвет")
    private List<String> color;

    @Schema(description = "тип")
    private List<String> type;

    @Schema(description = "дополнительное описание")
    private String description;

    @Schema(description = "Дата создания")
    private CreateDate createDate;

    @Schema(description = "Дата последнего обновления")
    private CreateDate lastUpdateDate;

}