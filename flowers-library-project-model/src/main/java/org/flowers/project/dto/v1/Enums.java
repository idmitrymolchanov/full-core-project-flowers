package org.flowers.project.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Enums {

    @Getter
    @AllArgsConstructor
    public enum SortingTypeEnum {
        ASC("ASC"),
        DESC("DESC");

        private final String code;
    }

    @Getter
    @AllArgsConstructor
    public enum SortingFieldEnum {
        COLOR("color"),
        TYPE("type"),
        SHORT_NAME("shortName"),
        CREATE_DATE("createDate");

        private final String code;
    }

}