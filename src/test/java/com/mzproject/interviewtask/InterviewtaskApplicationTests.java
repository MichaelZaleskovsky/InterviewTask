package com.mzproject.interviewtask;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InterviewtaskApplicationTests {

	@LocalServerPort
	private int port;
	
	private static String host = "http://localhost:";
	
	@Test
	void fileUpload() {
	
		given().contentType("multipart/form-data")							//Add records
		.multiPart("file", new File("test.txt"), "text/html")
		.when().post(host + port + "/client/records/")
		.then()
		.assertThat().body(containsString("All records added sucessfully"));

		given().contentType("multipart/form-data")							//Unique primary keys
		.multiPart("file", new File("test.txt"), "text/html")
		.when().post(host + port + "/client/records/")
		.then()
		.assertThat().body(containsString("Record contain not unique PRIMARY_KEY"));
		
		String expectedRecord = "{"
				+ "\"primaryKey\":10002,"
				+ "\"name\":\"Galina\","
				+ "\"description\":\"Russian cruel\","
				+ "\"timeStamp\":101"
				+ "}";
		
		given().contentType("application/json") 								//Get record by ID
		.when().get(host + port + "/client/records/10002") 
		.then() 
		.statusCode(HttpStatus.OK.value()) 
		.and()
		.assertThat().body(equalTo(expectedRecord));
		
		String expectedRecordsList = "["
				+ "{\"primaryKey\":10002,"
				+ "\"name\":\"Galina\","
				+ "\"description\":\"Russian cruel\","
				+ "\"timeStamp\":101},"
				
				+ "{\"primaryKey\":10003,"
				+ "\"name\":\"Piter\","
				+ "\"description\":\"America boy\","
				+ "\"timeStamp\":102},"
				
				+ "{\"primaryKey\":10004,"
				+ "\"name\":\"John\","
				+ "\"description\":\"Texas cowboy\","
				+ "\"timeStamp\":103},"
				
				+ "{\"primaryKey\":10005,"
				+ "\"name\":\"Jim Croce\","
				+ "\"description\":\"American singer\","
				+ "\"timeStamp\":104}"
				
				+ "]";

		given().contentType("application/json") 								//Get records by time
		.when().get(host + port + "/client/records/101/104") 
		.then() 
		.statusCode(HttpStatus.OK.value()) 
		.and()
		.assertThat().body(equalTo(expectedRecordsList));

		String expectedRecordsPagingList = "["
				+ "{\"primaryKey\":10004,"
				+ "\"name\":\"John\","
				+ "\"description\":\"Texas cowboy\","
				+ "\"timeStamp\":103},"
				
				+ "{\"primaryKey\":10005,"
				+ "\"name\":\"Jim Croce\","
				+ "\"description\":\"American singer\","
				+ "\"timeStamp\":104}"
				
				+ "]";

		given().contentType("application/json") 								//Get records by time with pagination
		.when().get(host + port + "/client/records/101/104?pageSize=2&pageNumber=2") 
		.then() 
		.statusCode(HttpStatus.OK.value()) 
		.and()
		.assertThat().body(equalTo(expectedRecordsPagingList));

	}

	@Test
	void WrongFileUpload() {													//Do not add wrong record
	
		given().contentType("multipart/form-data")
		.multiPart("file", new File("test-wrong.txt"), "text/html")
		.when().post(host + port + "/client/records/")
		.then()
		.assertThat().body(containsString("All records added sucessfully"));

		given().contentType("application/json") 
		.when().get(host + port + "/client/records/10007") 
		.then() 
		.statusCode(HttpStatus.NOT_FOUND.value()) 
		.and()
		.assertThat().body(equalTo("Record with ID 10007 not found"));
	}

	@Test
	void WrongFormatFileUpload() {
	
		given().contentType("multipart/form-data")
		.multiPart("file", new File("test-wrong-format.txt"), "text/html")
		.when().post(host + port + "/client/records/")
		.then()
		.assertThat().body(containsString("Wrong data format"));

	}

	@Test
	void DeleteRecord() {
	
		given().contentType("multipart/form-data")
		.multiPart("file", new File("test-delete.txt"), "text/html")
		.when().post(host + port + "/client/records/")
		.then()
		.assertThat().body(containsString("All records added sucessfully"));
		
		String expectedRecord = "{"
				+ "\"primaryKey\":10010,"
				+ "\"name\":\"Joane Roaling\","
				+ "\"description\":\"British writer\","
				+ "\"timeStamp\":111"
				+ "}";
		
		given().contentType("application/json") 								
		.when().get(host + port + "/client/records/10010") 
		.then() 
		.statusCode(HttpStatus.OK.value()) 
		.and()
		.assertThat().body(equalTo(expectedRecord));
		
		given().contentType("application/json") 								//Delete record by ID
		.when().delete(host + port + "/owner/records/10010") 
		.then() 
		.statusCode(HttpStatus.OK.value()) 
		.and()
		.assertThat().body(equalTo(expectedRecord));

		given().contentType("application/json") 
		.when().get(host + port + "/client/records/10010") 
		.then() 
		.statusCode(HttpStatus.NOT_FOUND.value()) 
		.and()
		.assertThat().body(equalTo("Record with ID 10010 not found"));
	}

}
