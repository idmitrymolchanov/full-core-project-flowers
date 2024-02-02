package org.flowers.project.dto.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.Valid;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Schema(description = "Дата и время создания/выполнения задачи")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateDate implements Serializable {

    private static final long serialVersionUID = 12345L;

    @Valid
    @JsonProperty("periodStart")
    @Schema(description = "Начало периода")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate periodStart;

    @Valid
    @JsonProperty("periodEnd")
    @Schema(description = "Конец периода")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate periodEnd;

}