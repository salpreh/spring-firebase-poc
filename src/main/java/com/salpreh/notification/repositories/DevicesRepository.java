package com.salpreh.notification.repositories;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class DevicesRepository {
    private final Set<String> devicesIds = new HashSet<>();

    public List<String> getAll() {
        return new ArrayList<>(devicesIds);
    }

    public void addDevice(String deviceId) {
        devicesIds.add(deviceId);
    }
}
