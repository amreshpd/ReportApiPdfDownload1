package com.nt.module;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInput {
    private String courseCategory;
    private String facultName;
    private String trainingMode;
    private LocalDateTime startOn;
    
}
