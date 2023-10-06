package fintech.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@ToString(includeFieldNames=true)
public class Weather {
    @Setter(AccessLevel.PRIVATE)
    private UUID regionId;
    private String regionName;
    private Integer temperature;
    private LocalDateTime date;
}
