package com.exam.ort.controller;

import com.exam.ort.model.SectionRecord;
import com.exam.ort.service.SectionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionController {

    final SectionService sectionService;

    @GetMapping
    public List<SectionRecord> getSections() {
        return sectionService.findAll();
    }

    @GetMapping("/{id}")
    public SectionRecord getSection(@PathVariable long id) {
        return sectionService.findById(id);
    }

    @PostMapping("/create")
    public SectionRecord createSection(@RequestBody SectionRecord section) {
        return sectionService.save(section);
    }

    @DeleteMapping("/{id}")
    public void deleteSection(@PathVariable long id) {
        sectionService.deleteById(id);
    }
}
