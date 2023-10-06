package com.fintech_spring.repositories;

import com.fintech_spring.models.Region;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class InMemoryRegionRepository implements RegionRepository {
    private final Map<UUID, Region> regionMap;

    public InMemoryRegionRepository(){
        regionMap = generateRegions();
    }

    @Override
    public List<Region> getAll() {
        return regionMap.values().stream().toList();
    }

    @Override
    public void add(Region region) {
        regionMap.put(region.getId(), region);
    }

    @Override
    public void delete(UUID id) {
        regionMap.remove(id);
    }

    @Override
    public Region getById(UUID id) {
        return regionMap.get(id);
    }

    @Override
    public Region getByName(String name) {
        return  getAll()
                .stream()
                .filter(x -> x.getName().equals(name))
                .findFirst().orElseThrow();
    }

    @Override
    public boolean exists(UUID id) {
        return regionMap.containsKey(id);
    }

    @Override
    public boolean exists(String name) {
        var region = getAll().stream()
                .filter(x -> x.getName().equals(name))
                .findFirst();

        return !region.isEmpty();
    }

    private Map<UUID, Region> generateRegions() { //пока что так
        var newRegions = new HashMap<UUID, Region>();

        newRegions.put(UUID.fromString("f140f6d2-ec7f-456e-9069-2ed463acdf28"),
                Region.builder()
                        .id(UUID.fromString("f140f6d2-ec7f-456e-9069-2ed463acdf28"))
                        .name("Saint-Petersburg")
                        .build());
        newRegions.put(UUID.fromString("92f3adc6-b6d4-4177-8280-84b8c9460ff4"),
                Region.builder()
                        .id(UUID.fromString("92f3adc6-b6d4-4177-8280-84b8c9460ff4"))
                        .name("Novosibirsk")
                        .build());
        newRegions.put(UUID.fromString("025afbe0-251e-4242-acdb-571c7aef9b84"),
                Region.builder()
                        .id(UUID.fromString("025afbe0-251e-4242-acdb-571c7aef9b84"))
                        .name("Moscow")
                        .build());
        newRegions.put(UUID.fromString("931f1ec4-36fd-4d2a-b3fd-2ac5d62492df"),
                Region.builder()
                        .id(UUID.fromString("931f1ec4-36fd-4d2a-b3fd-2ac5d62492df"))
                        .name("Berdsk")
                        .build());

        return newRegions;
    }
}
