package com.fintech_spring.models;

import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString(includeFieldNames=true)
public class Weather {
    @Setter(AccessLevel.PRIVATE)
    private UUID id;
    private UUID regionId; //TODO переделать на REgion
    private String regionName;
    private Integer temperature;
    private LocalDate date;
}
