package com.nt.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "JRTP_COURSE_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetails {
	@Id
	@GeneratedValue
    private Integer courseId;
	@Column(length = 50)
	private String courseName;
	@Column(length = 50)
	private String courseCategory;
	@Column(length = 50)
	private String facultyName;
	@Column(length = 50)
	private String courseStatus;
	@Column(length = 50)
	private String location;

	@Column(length = 12)
	private Double courseFee;
	@Column(length = 50)
	private String adminName;
	@Column(length = 15)
	private Long adminContactNumber;
	@Column(length = 40)
	private String trainingMode;
	@Column
	private LocalDateTime startDate;
	
	@Column(insertable = true,updatable = false)
	@CreationTimestamp
	private LocalDateTime creationDate;
	@UpdateTimestamp
	@Column(insertable = false,updatable = true)
	private LocalDateTime updationDate;
	
	@Column(length = 50)
	private String createdBy;
	@Column(length = 50)
	private String updatedBy;
}
