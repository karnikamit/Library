package io.springworks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.springworks.exceptions.LibraryExceptions;
import io.springworks.models.Ledger;
import io.springworks.models.Response;
import io.springworks.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryController {

	private static Logger logger = LoggerFactory.getLogger(LibraryController.class);

	@Autowired
	LibraryService libService;

	@PostMapping("/rent")
	public Response rentBook(@RequestBody Ledger record) {
		Response response = new Response();
		try{
			libService.vaildateRecord(record, true, true);
			logger.info("Record sucessfully validated");
		}catch (LibraryExceptions e){
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setErrorCode(HttpStatus.BAD_REQUEST.value());
			response.setFailureReason(e.getMessage());
			logger.error(e.getMessage());
			return response;
		}
		int ledgerId = libService.rentBook(record);
		if(ledgerId > 0) {
			List<Map<String, Integer>> ledgerList = new ArrayList<>();
			Map<String, Integer> ledgerMap = new HashMap<>();
			ledgerMap.put("Ledger ID", ledgerId);
			ledgerList.add(ledgerMap);
			response.setIsSuccessful(Boolean.TRUE);
			response.setHttpStatus(HttpStatus.CREATED);
			response.setObjects(ledgerList);
			
		}else {
			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			logger.error("INTERNAL_SERVER_ERROR!");
		}
		return response;
	}

	@PostMapping("/return")
	public Response returnBook(@RequestBody Ledger record) {
		Response response = new Response();
		try{
			libService.vaildateRecord(record, false, true);	// Book wont be present in the lib
			logger.info("User sucessfully validated");
		}catch (LibraryExceptions e){
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setErrorCode(HttpStatus.BAD_REQUEST.value());
			response.setFailureReason(e.getMessage());
			logger.error(e.getMessage());
			return response;
		}
		return response;
	}
}
