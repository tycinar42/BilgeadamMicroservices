package com.tyc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Competititon {
    private String id;
    private String name;
    private String description;
    private Long startDate;
    private Long endDate;
    private String userId; //creator
    private String status;
}
