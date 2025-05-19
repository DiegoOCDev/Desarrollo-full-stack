package com.API.API.controller;

import com.API.API.model.Instructor;
import org.springframework.web.bind.annotation.RestController;
import com.API.API.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Instructors")

class InstructorController {
    @Autowired
    private InstructorService instructorService;
    @GetMapping
    public List<Instructor> getAllInstructors() {
        return instructorService.getAllIntructores();
    }
    @GetMapping("/{id}")
    public String  getInstructorById(@PathVariable int id) {
        return instructorService.getInstructor(id);
    }
    @PostMapping
    public Instructor addInstructor(@RequestBody Instructor instructor) {
        return instructorService.addInstructor(instructor);
    }
    @PutMapping("/{id}")
    public String updateInstructor(@PathVariable int id, @RequestBody Instructor instructor) {
       return instructorService.updateInstructor(id ,instructor);
    }
    @DeleteMapping("/{id]")
    public String deleteInstructor(@PathVariable int id) {
        return instructorService.deleteInstructor(id);
    }

}
