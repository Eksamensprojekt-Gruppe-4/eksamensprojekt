package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.Manager;
import com.banditdev.eksamensprojekt.repository.ManagerRepository;
import org.springframework.stereotype.Service;


@Service
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public void updateManager(Manager manager) {
        managerRepository.updateManager(manager);
    }

    //TODO validateUser()
    //TODO findManagerByUsername()
}
