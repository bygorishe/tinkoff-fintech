package com.fintech_spring.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@ToString(includeFieldNames=true)
public class Region {
    @Setter(AccessLevel.PRIVATE)
    private UUID id;
    private String name;
}
