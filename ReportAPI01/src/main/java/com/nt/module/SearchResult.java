package com.nt.module;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
	private Integer courseId;
	private String courseName;
	private String courseCategory;
	private String facultyName;
	private String courseStatus;
	private String location;
	private Double courseFee;
	private String adminName;
	private Long adminContactNumber;
	private String trainingMode;
	private LocalDateTime startDate;
}
