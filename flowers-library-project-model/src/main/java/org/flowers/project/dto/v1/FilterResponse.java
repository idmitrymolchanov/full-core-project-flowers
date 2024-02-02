package org.flowers.project.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Schema
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterResponse implements Serializable {

    private static final long serialVersionUID = 12345L;

    @Schema(description = "Общее количество в выборке")
    private Integer totalCount;

    @Schema(description = "Количество записей на странице")
    private Integer limit;

    @Schema(description = "Смещение от начала списка")
    private Integer offset;

    @Schema(description = "растения")
    private List<Flower> flowerList;

}