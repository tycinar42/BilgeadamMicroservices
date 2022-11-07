package com.tyc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class QuestionListOnCompetition {
    @Id
    private String id;
    private String competitionId;
    private String questionId;
    private Integer questionNumber;
    private Boolean isAnswered;
}
