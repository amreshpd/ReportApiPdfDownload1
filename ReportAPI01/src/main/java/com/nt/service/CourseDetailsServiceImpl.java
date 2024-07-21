package com.nt.service;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.entity.CourseDetails;
import com.nt.module.SearchInput;
import com.nt.module.SearchResult;
import com.nt.repository.ICourseDetailsRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CourseDetailsServiceImpl implements ICourseService{
	
	@Autowired
	private ICourseDetailsRepository repo;
	@Override
	public Set<String> showAllCourseCategory() {
		return repo.getUniqueCourseCategories();
	}

	@Override
	public Set<String> showAllFaculties() {		
		return repo.getUniqueFacultiesName();
	}

	@Override
	public Set<String> showAllTrainingMode() {		
		return repo.getUniqueTrainingMode();
	}

	@Override
	public List<SearchResult> showAllCourseByFilter(SearchInput input) {
	//get NonNull and empty string value from the input object inside Example object
	// object having that not null data and also place the entity object inside example obj
	/*	CourseDetails entity = new CourseDetails();
		String courseCategory = input.getCourseCategory();		
		if(courseCategory!=null && !courseCategory.equals("") && courseCategory.length()!=0)
			entity.setCourseCategory(courseCategory);
		String facultName = input.getFacultName();
		if(facultName!=null && !facultName.equals("") && facultName.length()!=0)
			entity.setFacultyName(facultName);
		String trainingMode = input.getTrainingMode();
		if(!trainingMode.equals("") && trainingMode!=null && trainingMode.length()!=0) 
			entity.setTrainingMode(trainingMode);
		
		LocalDateTime startOn = input.getStartOn();
		if(startOn!=null) 
			entity.setStartDate(startOn);
		Example<CourseDetails> example = Example.of(entity);
		//perform the search operation on filter data with entity example obj
		List<CourseDetails> listEntities = repo.findAll(example);
		  //convert the list<entity object> to search<result obj> 
	    ArrayList<SearchResult> listResult=new ArrayList<SearchResult>();
	    listEntities.forEach(course->{
	    	SearchResult result = new SearchResult();
	    	BeanUtils.copyProperties(course, result);
	    	listResult.add(result);
	    });		
		*/
		
	    CourseDetails entity = new CourseDetails();
		String courseCategory = input.getCourseCategory();
		if(StringUtils.hasLength(courseCategory))
			entity.setCourseCategory(courseCategory);
		String facultName = input.getFacultName();
		if(StringUtils.hasLength(facultName))
			entity.setFacultyName(facultName);
		String trainingMode = input.getTrainingMode();
		if(StringUtils.hasLength(trainingMode))
			entity.setTrainingMode(trainingMode);
		LocalDateTime startOn = input.getStartOn();
		    entity.setStartDate(startOn);
		    Example<CourseDetails> example = Example.of(entity);
		//perform the search operation which filter the data of the example Entity obj 
		    List<CourseDetails> listEntities = repo.findAll(example);
		    //convert the list<entity object> to search<result obj> 
		    ArrayList<SearchResult> listResult=new ArrayList<SearchResult>();
		    listEntities.forEach(course->{
		    	SearchResult result = new SearchResult();
		    	BeanUtils.copyProperties(course, result);
		    	listResult.add(result);
		    });
		    		    
		return listResult;	
}

	@Override
	public void generatedPdfRepot(SearchInput input, HttpServletResponse response) throws Exception{
	    //get the search results
		List<SearchResult> listResult = showAllCourseByFilter(input);
	    //create pdf file
		Document document = new Document(PageSize.A4);
		//get pdfWriter to write the document and response object 
	    PdfWriter.getInstance(document, response.getOutputStream());
		//open the document
	    document.open();
	    // Define Font for the paragraph
	    Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(30);
		font.setColor(Color.CYAN);
		
	    //create the paragraph having content and above font style
		Paragraph para =new Paragraph("Search report of the course ",font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		//add paragraph to document
	    document.add(para);
	    //display search result as pdf table
	    PdfPTable table=new PdfPTable(10);
	    table.setWidthPercentage(70.0f);
	    table.setWidths(new float[] {3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.f,3.0f,3.0f,3.0f});
	    table.setSpacingBefore(2.0f);
	    
	    // prepare heading row cells in the pdf
	    PdfPCell cell=new PdfPCell();
	    cell.setBackgroundColor(Color.CYAN);
	    cell.setPadding(5);
	    Font fontCell = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	    fontCell.setColor(Color.BLACK);
	    
	    cell.setPhrase(new Phrase("course Id", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("course Name", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("course Category", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("faculty Name", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("course Status", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("location", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("course Fee", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("training Mode", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("admin Contact Number", fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("start Date", fontCell));
	    table.addCell(cell);
	    //add data cells to pdf table
	    
	    listResult.forEach(result->{
	    	table.addCell(String.valueOf(result.getCourseId()));
	    	table.addCell(result.getCourseName());
	    	table.addCell(result.getCourseCategory());
	    	table.addCell(result.getFacultyName());
	    	table.addCell(result.getCourseStatus());
	    	table.addCell(result.getLocation());
	    	table.addCell(String.valueOf(result.getCourseFee()));
	    	table.addCell(result.getTrainingMode());
	    	table.addCell(String.valueOf(result.getAdminContactNumber()));
	    	table.addCell(result.getStartDate().toString());
	    	    	
	    });
	    // add table to document
	    document.add(table);
	    //close the document
	    document.close();
		
	}

	@Override
	public void generatedExcelRepot(SearchInput input, HttpServletResponse response) throws Exception {
		//get the Search result 
		List<SearchResult> listResult=showAllCourseByFilter(input);
		
		// create the ExcelWorkBook (Apache POI App)
		HSSFWorkbook workBook = new HSSFWorkbook();
		//create sheet in the Work book
		HSSFSheet sheet1 = workBook.createSheet("course details");
		//create Heading Row in sheet1
		HSSFRow headerRow = sheet1.createRow(0);
		headerRow.createCell(0).setCellValue("courseId");
		headerRow.createCell(1).setCellValue("courseName");
		headerRow.createCell(2).setCellValue("courseCategory");
		headerRow.createCell(3).setCellValue("facultyName");
		headerRow.createCell(4).setCellValue("courseStatus");
		headerRow.createCell(5).setCellValue("location");
		headerRow.createCell(6).setCellValue("courseFee");
		headerRow.createCell(7).setCellValue("adminContactNumber");
		headerRow.createCell(8).setCellValue("trainingMode");
		headerRow.createCell(9).setCellValue("startDate");
		
		//add data row in the sheet
		int i=1;
		for(SearchResult result: listResult) {
		HSSFRow row = sheet1.createRow(i);
			row.createCell(0).setCellValue(result.getCourseId());
			row.createCell(1).setCellValue(result.getCourseName());
			row.createCell(2).setCellValue(result.getCourseCategory());
			row.createCell(3).setCellValue(result.getFacultyName());
			row.createCell(4).setCellValue(result.getCourseStatus());
			row.createCell(5).setCellValue(result.getLocation());
			row.createCell(6).setCellValue(result.getCourseFee());
			row.createCell(7).setCellValue(result.getAdminContactNumber());
			row.createCell(8).setCellValue(result.getTrainingMode());
			row.createCell(9).setCellValue(result.getStartDate().toString());
			i++;
		}
		
		//get output stream pointing to response obj
		ServletOutputStream outputStream = response.getOutputStream();
		//write the excel workbook data response object using the above stream
		workBook.write(outputStream);
		//close the stream
		outputStream.close();
		workBook.close();	
		
	}

	@Override
	public void generatePdfReportAllData(HttpServletResponse res) throws Exception {
		//get all the record from db table 
		List<CourseDetails> list = repo.findAll();
	// List<CourseDetails> to List<SearchDetails>
		List<SearchResult> listResult = new ArrayList();
		list.forEach(course->{
			SearchResult searchResult = new SearchResult();
			BeanUtils.copyProperties(course,searchResult);
			listResult.add(searchResult);
		});
		Document document = new Document(PageSize.A4);
		//get pdfWriter to write the document and response object 
	    PdfWriter.getInstance(document,res.getOutputStream());
		//open the document
	    document.open();
	    // Define Font for the paragraph
	    Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(30);
		font.setColor(Color.CYAN);
		
	    //create the paragraph having content and above font style
		Paragraph para =new Paragraph("Search report of the course ",font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		//add paragraph to document
	    document.add(para);
	    
	    //display search result as Pdf table
	    PdfPTable table=new PdfPTable(10);
	    table.setWidthPercentage(80.0f);
	    table.setWidths(new float[] {3.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.f,3.0f,3.0f,3.0f});
	    table.setSpacingBefore(2.0f);
	    
	    // prepare heading row cells in the pdf
	    PdfPCell cell=new PdfPCell();
	    cell.setBackgroundColor(Color.CYAN);
	    cell.setPadding(5);
	    Font fontCell = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	    fontCell.setColor(Color.BLACK);
	    
	    cell.setPhrase(new Phrase("courseId",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("courseName",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("courseCategory",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("facultyName",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("courseStatus",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("location",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("courseFee",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("trainingMode",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("adminContactNumber",fontCell));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase("startDate",fontCell));
	    table.addCell(cell);
	    //add data cells to pdf table
	    
	    listResult.forEach(result->{
	    	table.addCell(String.valueOf(result.getCourseId()));
	    	table.addCell(result.getCourseName());
	    	table.addCell(result.getCourseCategory());
	    	table.addCell(result.getFacultyName());
	    	table.addCell(result.getCourseStatus());
	    	table.addCell(result.getLocation());
	    	table.addCell(String.valueOf(result.getCourseFee()));
	    	table.addCell(result.getTrainingMode());
	    	table.addCell(String.valueOf(result.getAdminContactNumber()));
	    	table.addCell(result.getStartDate().toString());	    	    	
	    });
	    // add table to document
	    document.add(table);
	    //close the document
	    document.close();
		
	}

	@Override
	public void generateExcelReportAllData(HttpServletResponse res) throws Exception {
		//get all the record from Db table 
		List<CourseDetails> list = repo.findAll();
	// List<CourseDetails> to List<SearchDetails>
		ArrayList<SearchResult> listResult = new ArrayList();
		list.forEach(course->{
			SearchResult searchResult = new SearchResult();
			BeanUtils.copyProperties(course,searchResult);
			listResult.add(searchResult);
		});
		// create the ExcelWorkBook ( Apache Poi App )
				HSSFWorkbook workBook = new HSSFWorkbook();
				//create sheet in the Work book
				HSSFSheet sheet1 = workBook.createSheet("course details");
				//create Heading Row in sheet1
				HSSFRow headerRow = sheet1.createRow(0);
				headerRow.createCell(0).setCellValue("courseId");
				headerRow.createCell(1).setCellValue("courseName");
				headerRow.createCell(2).setCellValue("courseCategory");
				headerRow.createCell(3).setCellValue("facultyName");
				headerRow.createCell(4).setCellValue("courseStatus");
				headerRow.createCell(5).setCellValue("location");
				headerRow.createCell(6).setCellValue("courseFee");
				headerRow.createCell(7).setCellValue("adminContactNumber");
				headerRow.createCell(8).setCellValue("trainingMode");
				headerRow.createCell(9).setCellValue("startDate");
				
				//add data row in the sheet
				int i=1;
				for(SearchResult result: listResult) {
				HSSFRow row = sheet1.createRow(i);
					row.createCell(0).setCellValue(result.getCourseId());
					row.createCell(1).setCellValue(result.getCourseName());
					row.createCell(2).setCellValue(result.getCourseCategory());
					row.createCell(3).setCellValue(result.getFacultyName());
					row.createCell(4).setCellValue(result.getCourseStatus());
					row.createCell(5).setCellValue(result.getLocation());
					row.createCell(6).setCellValue(result.getCourseFee());
					row.createCell(7).setCellValue(result.getAdminContactNumber());
					row.createCell(8).setCellValue(result.getTrainingMode());
					row.createCell(9).setCellValue(result.getStartDate().toString());
					i++;
				}
				
				//get output stream pointing to response object
				ServletOutputStream outputStream = res.getOutputStream();
				//write the excel workbook data response object using the above straem
				workBook.write(outputStream);
				//close the stream
				outputStream.close();
				workBook.close();	
		
	}

}
