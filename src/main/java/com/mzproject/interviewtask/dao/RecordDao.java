package com.mzproject.interviewtask.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mzproject.interviewtask.entities.Record;


@Repository
@Transactional
public class RecordDao {
	
	private Logger log = Logger.getLogger(RecordDao.class.getName());
	
	EntityManager em;
	Environment env;

	@Autowired
	public RecordDao(EntityManager em, Environment env) {
		this.em = em;
		this.env = env;
	}

	public HttpStatus addRecords(List<Record> records) {
		int batchSize = Integer.parseInt(env.getProperty("spring.jpa.properties.hibernate.jdbc.batch_size"));
		
		for (int i = 0; i < records.size(); i++) {
			
			try {
				em.persist(records.get(i));
			} catch (Exception e) {
				log.log(Level.WARNING, "PERSIST ERROR" + records.get(i).toString() + e.toString());
			}
			
			if ((i+1) % batchSize == 0 || i == (records.size()-1)) {
				try {
					em.flush();
				} catch (Exception e) {
					log.log(Level.WARNING, "FLUSH ERROR" + records.get(i).toString() + e.toString());
					return HttpStatus.CONFLICT;
				}
				em.clear();
			}
		};
		
		return HttpStatus.CREATED;
	}

	public Record getRecordById(long id) {
		
		return em.find(Record.class, id);
	}

	public List<Record> getRecordsByTime(long timeFrom, long timeTo, int pageNumber, int pageSize) {
		Query query = em.createQuery("select r from Record r where r.timeStamp between ?1 and ?2")
		.setParameter(1, timeFrom)
		.setParameter(2, timeTo)
		.setFirstResult((pageNumber-1) * pageSize)
		.setMaxResults(pageSize);
				
		return query.getResultList();
	}

	public Record deleteRecord(long id) {
		Record record = em.find(Record.class, id);
		if (record != null) {
			em.remove(record);
		}
		return record;
	}

}
