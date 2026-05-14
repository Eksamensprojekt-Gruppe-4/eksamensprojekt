package com.banditdev.eksamensprojekt.controller;

import com.banditdev.eksamensprojekt.model.SubProject;
import com.banditdev.eksamensprojekt.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/subprojects")
public class SubProjectController {
    private final SubProjectService service;

    public SubProjectController(SubProjectService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String showSubProjectForm(@PathVariable int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("subProject", new SubProject());

        return "subProjectCreate";
    }

    @PostMapping
    public String createSubProject(@PathVariable int projectId,
                           @ModelAttribute SubProject subProject) {
        subProject.setProjectId(projectId);
        service.createSubProject(subProject);

        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/{subProjectId}/delete")
    public String deleteSubProject(@PathVariable int projectId,
                                   @PathVariable int subProjectId) {
        service.deleteSubProjectById(subProjectId);
        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/{subProjectId}/edit")
    public String showEditProject(@PathVariable int projectId,
                                  @PathVariable int subProjectId,
                                  Model model) {
        model.addAttribute("subProject", service.findSubProjectBySubProjectId(subProjectId));
        model.addAttribute("projectId", projectId);
        return "editSubProject";
    }

    @PostMapping("/{subProjectId}/edit")
    public String editSubProject(@PathVariable int projectId,
                                 @PathVariable int subProjectId,
                                 @ModelAttribute SubProject subProject){
        subProject.setSubProjectId(subProjectId);
        subProject.setProjectId(projectId);
        service.updateSubProject(subProject);
        return "redirect:/projects/" + projectId;
    }
}
