package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {

	public List<Student> theStudents;

	@PostConstruct
	public void loadData() {
		 theStudents = new ArrayList<Student>();

		theStudents.add(new Student("mario", "trewa"));
		theStudents.add(new Student("heera", "jhywa"));
		theStudents.add(new Student("virano", "utewaa"));

	}

	@GetMapping("/students")
	public List<Student> getStudents() {

		return theStudents;

	}
	
	@GetMapping("/students/{studentId}")
	public Student getStudents(@PathVariable int studentId)
	{
		
		if((studentId>=theStudents.size()||(studentId<0)))
		{
			throw new StudentNotFoundException("student id is not found-"+studentId);
		
	}
		return theStudents.get(studentId);
	
	}
	//Add an exception handler
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
	
	
		StudentErrorResponse error =new StudentErrorResponse();
	 error.setStatus(HttpStatus.NOT_FOUND.value());
	 error.setMessage(exc.getMessage());
	 error.setTimeStamp(System.currentTimeMillis());
	 
	 return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler
	public ResponseEntity<StudentErrorResponse> handleException(Exception exc){
	
	
		StudentErrorResponse error =new StudentErrorResponse();
	 error.setStatus(HttpStatus.BAD_REQUEST.value());
	 error.setMessage(exc.getMessage());
	 error.setTimeStamp(System.currentTimeMillis());
	 
	 return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
}
}
	
