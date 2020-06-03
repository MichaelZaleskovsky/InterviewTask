package com.mzproject.interviewtask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mzproject.interviewtask.entities.Record;
import com.mzproject.interviewtask.services.RecordService;

@RestController
@RequestMapping(value="owner/records")
public class OwnerController {
	
	RecordService recordService;
	
	@Autowired
	public OwnerController(RecordService recordService) {
		this.recordService = recordService;
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	ResponseEntity deleteRecord(@PathVariable long id) {
		Record record = recordService.deleteRecord(id);
		
		if (record == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record with ID " + id + " not found");
		} else {
			return ResponseEntity.ok(record);
		}
	}

}
