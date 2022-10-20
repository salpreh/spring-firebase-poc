package com.salpreh.notification.controllers.actuators;

import com.salpreh.notification.repositories.DevicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Endpoint(id = "messaging")
public class MessagingActuator {
    private final DevicesRepository devicesRepository;

    @ReadOperation
    public List<String> knownDevices() {
        return devicesRepository.getAll();
    }
}
