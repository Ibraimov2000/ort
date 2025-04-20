package com.exam.ort.model.request;

import lombok.Data;

@Data
public class SectionRequest {
    private Long id;
    private String name;
    private int questionsCount;
}
