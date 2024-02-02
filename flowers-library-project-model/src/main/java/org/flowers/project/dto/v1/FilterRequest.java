package org.flowers.project.dto.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterRequest implements Serializable {

    private static final long serialVersionUID = 12345L;

    @Valid
    @NotNull
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, description = "Количество записей на странице")
    private Integer limit;

    @Valid
    @NotNull
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, description = "Смещение от начала списка")
    private Integer offset;

    private FlowersFilter filter;

    private FlowersSort sort;

}