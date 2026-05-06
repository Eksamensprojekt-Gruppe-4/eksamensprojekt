package com.banditdev.eksamensprojekt.service;

import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.repository.SubProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubProjectService {
    private final SubProjectRepository spRepo;

    public SubProjectService(SubProjectRepository spRepo) {
        this.spRepo = spRepo;
    }

    public void createSubProject(SubProject sp) {
        spRepo.saveSubProject(sp);
    }
}
