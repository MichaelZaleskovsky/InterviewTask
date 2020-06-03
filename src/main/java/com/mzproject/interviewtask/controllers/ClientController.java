package com.mzproject.interviewtask.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mzproject.interviewtask.entities.Record;
import com.mzproject.interviewtask.services.RecordService;

@RestController
@RequestMapping(value="client/records")
public class ClientController {
	
	private final int DEFAULT_PAGE = 1;
	private final int DEFAULT_SIZE = 25;
	
	RecordService recordService;
	
	@Autowired
	public ClientController(RecordService recordService) {
		this.recordService = recordService;
	}

	@PostMapping("/")
	ResponseEntity<String> addRecords(@RequestParam("file") MultipartFile file) {
		
		HttpStatus status = recordService.addRecords(file);
		String message;
		
		switch (status.value()) {
		case 201: 
			message = "All records added sucessfully";
			break;
		case 400: 
			message = "Inner mistake";
			break;
		case 406: 
			message = "Wrong data format";
			break;
		case 409: 
			message = "Record contain not unique PRIMARY_KEY";
			break;
		default:
			message = "Unexpected error";
		}
		
		return ResponseEntity.status(status).body(message);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	ResponseEntity getRecord(@PathVariable long id) {
		
		Record record = recordService.getRecordById(id);
		
		if (record == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record with ID " + id + " not found");
		} else {
			return ResponseEntity.ok(record);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/{timeFrom}/{timeTo}")
	ResponseEntity getRecords(@PathVariable long timeFrom, @PathVariable long timeTo, 
								@RequestParam @Nullable Optional<Integer> pageNumber, 
								@RequestParam @Nullable Optional<Integer> pageSize) {
		
		List<Record> records = recordService.getRecordsByTime(timeFrom, timeTo, pageNumber.orElse(DEFAULT_PAGE), pageSize.orElse(DEFAULT_SIZE));
		
		return ResponseEntity.status(HttpStatus.OK).body(records);
	}
}
