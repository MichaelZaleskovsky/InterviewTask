package com.mzproject.interviewtask.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.mzproject.interviewtask.entities.Record;

public interface RecordService {

	HttpStatus addRecords(MultipartFile file);

	Record getRecordById(long id);

	List<Record> getRecordsByTime(long timeFrom, long timeTo, Integer pageNumber, Integer pageSize);

	Record deleteRecord(long id);

}
