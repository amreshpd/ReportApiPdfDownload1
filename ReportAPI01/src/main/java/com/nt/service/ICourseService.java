package com.nt.service;

import java.util.List;
import java.util.Set;

import com.nt.module.SearchInput;
import com.nt.module.SearchResult;

import jakarta.servlet.http.HttpServletResponse;

public interface ICourseService {
	public Set<String> showAllCourseCategory();
	public Set<String> showAllFaculties();
	public Set<String> showAllTrainingMode();
	
	public List<SearchResult> showAllCourseByFilter(SearchInput input);
	
	public void generatedPdfRepot(SearchInput input,HttpServletResponse response) throws Exception;
	public void generatedExcelRepot(SearchInput input,HttpServletResponse response) throws Exception;
	public void generatePdfReportAllData(HttpServletResponse res) throws Exception;
	public void generateExcelReportAllData(HttpServletResponse res) throws Exception;
}
