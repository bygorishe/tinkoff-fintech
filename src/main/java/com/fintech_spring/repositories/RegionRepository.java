package com.fintech_spring.repositories;

import com.fintech_spring.models.Region;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegionRepository {
    public List<Region> getAll();
    public void add(Region region);
    public void delete(UUID id);
    public Region getById(UUID id);
    public Region getByName(String name);
    public boolean exists(UUID id);
    public boolean exists(String name);
}
