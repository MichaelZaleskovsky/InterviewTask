package com.mzproject.interviewtask.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mzproject.interviewtask.entities.Record;
import com.mzproject.interviewtask.services.RecordService;
import com.mzproject.interviewtask.dao.RecordDao;

@Service
public class RecordServiceImpl implements RecordService {
	
	private Logger log = Logger.getLogger(RecordServiceImpl.class.getName());
	
	private final String HEADER = "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP";
	private final int RECORD_SIZE = 4;
	
	RecordDao recordDao;
	
	@Autowired
	public RecordServiceImpl(RecordDao recordDao) {
		super();
		this.recordDao = recordDao;
	}

	@Override
	public HttpStatus addRecords(MultipartFile file) {
		
		InputStream input = null;
		try {
			input = file.getInputStream();
		} catch (IOException e) {
			log.log(Level.WARNING, e.toString());
			return HttpStatus.BAD_REQUEST;
		}
		Scanner scanner = new Scanner(input);
		
		if (file.isEmpty() || !scanner.nextLine().equals(HEADER)) return HttpStatus.NOT_ACCEPTABLE;
		
		String line;
		String[] tokens;
		List<Record> records = new ArrayList<>();
		
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			
			if (!line.isBlank()) {
				tokens = line.split(",");
				if (tokens.length >= RECORD_SIZE) {
					try {
						Record record = new Record(Long.parseLong(tokens[0]), tokens[1], tokens[2], Long.parseLong(tokens[3]));
						records.add(record);
					} catch (NumberFormatException e) {
						log.log(Level.WARNING, e.toString());
					}
				}
			}
		}
		
		HttpStatus result;
		try {
			result = recordDao.addRecords(records);
		} catch (Exception e) {
			log.log(Level.WARNING, e.toString());
			return HttpStatus.CONFLICT;
		}
		
		return result;

	}

	@Override
	public Record getRecordById(long id) {
		return recordDao.getRecordById(id);
	}

	@Override
	public List<Record> getRecordsByTime(long timeFrom, long timeTo, Integer pageNumber, Integer pageSize) {
		return recordDao.getRecordsByTime(timeFrom, timeTo, pageNumber.intValue(), pageSize.intValue());
	}

	@Override
	public Record deleteRecord(long id) {
		return recordDao.deleteRecord(id);
	}

}
