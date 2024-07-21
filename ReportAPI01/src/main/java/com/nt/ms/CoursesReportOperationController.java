package com.nt.ms;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.module.SearchInput;
import com.nt.module.SearchResult;
import com.nt.service.ICourseService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/course/api")
@OpenAPIDefinition(info = 
@Info(
		title = "Reporting Api",
		version ="1.0",
		description="Reporting API supporting download File in pdf or Excel format",
		license=@License(name = "AMRESH KUMAR ",url = "www.google.com"),
		contact=@Contact(url = "https://gigantic-server.com",name = "Amresh",email = "amreshpd98@gmil.com")
		)
)
public class CoursesReportOperationController {
	 @Autowired
     private ICourseService service;
    
	 @Operation(
		      summary = "Fetch Course Categories",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})	 
	 @Tag(name = "Course Categories", description = "Report API")
    @GetMapping("/courses")
    public ResponseEntity<?> fetchCourseCategories(){
    	try {
    		Set<String> courseInfo = service.showAllCourseCategory();
    		return new ResponseEntity<Set<String>>(courseInfo,HttpStatus.OK);
    	}catch (Exception e) {
		    return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
   }
	 @Operation(
		      summary = "Fetch All Training Mode",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})
	 @Tag(name = "Training Mode", description = "Report API")
    @GetMapping("/training-mode")
    public ResponseEntity<?> fetchTrainingMode(){
    	try {
    		//use service 
    		Set<String> showAllTrainingMode = service.showAllTrainingMode();
    		return new ResponseEntity<Set<String>>(showAllTrainingMode,HttpStatus.OK);
    	}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	 @Operation(
		      summary = "Fetch All facultioes",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})	
	 @Tag(name = "Faculties", description = "Report API")
    @GetMapping("/facltuies")
    public ResponseEntity<?> fetchFacluties(){
    	try {
    		//use services
    		Set<String> showAllFaculties = service.showAllFaculties();
    		return new ResponseEntity<Set<String>>(showAllFaculties,HttpStatus.OK);    		
    	}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	 @Operation(
		      summary = "Fetch Course By Filter",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})	 
	 @Tag(name = "Fetch Filter", description = "Report API")
    @PostMapping("/search")
    public ResponseEntity<?> fetchCourseByFilter(@RequestBody SearchInput input){
    	try {
    		List<SearchResult> showAllCourseByFilter = service.showAllCourseByFilter(input);
    		return new ResponseEntity<List<SearchResult>>(showAllCourseByFilter,HttpStatus.OK);
    	}catch (Exception e) {
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}    	
    }
	 @Operation(
		      summary = "Download Pdf File with search of the Content",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})	
	 @Tag(name = "Download PDF with Condition", description = "Report API")
    @PostMapping("/pdf-report")
    public void showPdfReport(@RequestBody SearchInput input,HttpServletResponse res) {
    	try {
    		//set the response content type
    		res.setContentType("application/pdf");
    		//set-the content-disposition header to response content going to browser as downloadable pdf
    		res.setHeader("Content-Disposition", "attachment:fileName=courses.pdf");
    		//use service
    		service.generatedPdfRepot(input, res);  		
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
	 @Operation(
		      summary = "Download Excel file with content",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})	
	 @Tag(name = "Download Excel with condition", description = "Report API")
    @PostMapping("/excel-report")
    public void showExcelReport(@RequestBody SearchInput input,HttpServletResponse res) {
    	try {
    		//set the response content type
    		res.setContentType("application/vmd.ms-excel");
    		//set-the content-disposition header to response content going to browser as download able pdf
    		res.setHeader("Content-Disposition", "attachment:fileName=courses.xls");
    		//use service
    		service.generatedExcelRepot(input, res);  		
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
	 @Operation(
		      summary = "Download All Pdf file",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})	
	 @Tag(name = "PDF Download", description = "Report API")
    @GetMapping("/all-pdf-report")
    public void showAllPdfReport(HttpServletResponse res) {
    	try {
    		//set the response content type
    		res.setContentType("application/pdf");
    		//set-the content-disposition header to response content going to browser as download able pdf
    		res.setHeader("Content-Disposition", "attachment:fileName=courses.pdf");
    		//use service
    		service.generatePdfReportAllData(res);  		
    		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
	 @Operation(
		      summary = "Download All Excel file",
		     responses = {
		    		 @ApiResponse(description = "CourseInfo",
		    		 content=@Content(mediaType = "application/json",
		    		 schema= @Schema(implementation = String.class))),
		    		 @ApiResponse(responseCode = "500",description = "Wrong Url")})	
    @GetMapping("/all-excel-report")
	 @Tag(name = "Download all excel", description = "Report API")
    public void showAllExcelReport(HttpServletResponse res) {
    	try {
    		//set the response content type
    		res.setContentType("application/vmd.ms-excel");
    		//set-the content-disposition header to response content going to browser as download able pdf
    		res.setHeader("Content-Disposition", "attachment:fileName=courses.xls");
    		//use service
    		service.generateExcelReportAllData(res);      		
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
 }
