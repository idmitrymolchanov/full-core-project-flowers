package org.flowers.project.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.validation.Valid;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Schema
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlowersSort implements Serializable {

    private static final long serialVersionUID = 12345L;

    @Valid
    @JsonProperty("order")
    @Schema(description = "Способ сортировки", defaultValue = "DESC")
    private Enums.SortingTypeEnum order;

    @Valid
    @JsonProperty("fieldName")
    @Schema(description = "Название поля", defaultValue = "createDate")
    private Enums.SortingFieldEnum fieldName;

}