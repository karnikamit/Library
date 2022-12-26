package io.springworks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.springworks.models.Ledger;
import io.springworks.models.Response;
import io.springworks.repos.LedgerRepo;
import io.springworks.service.LedgerService;

@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

	private static Logger logger = LoggerFactory.getLogger(LedgerController.class);

	@Autowired
	LedgerService ledgerService;

	@GetMapping("/records")
	public Response getRecords() {
		Response response = new Response();
		List<Ledger> records = ledgerService.getRecords();
		if(CollectionUtils.isEmpty(records)) {
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setErrorCode(HttpStatus.NOT_FOUND.value());
			logger.error("NO records found!");
			return response;
		}
		logger.info("Found {} records", records.size());
		response.setIsSuccessful(Boolean.TRUE);
		response.setHttpStatus(HttpStatus.FOUND);
		response.setObjects(records);
		response.setErrorCode(0);
		return response;
	}

	@GetMapping("/id")
	public Response getRecord(@RequestParam("id") int id) {
		Response response = new Response();
		List<Ledger> records = new ArrayList<>();
		Ledger record = ledgerService.getRecord(id);
		if(record == null) {
			response.setIsSuccessful(Boolean.FALSE);
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response .setErrorCode(HttpStatus.NOT_FOUND.value());
			return response;
		}
		response.setIsSuccessful(Boolean.TRUE);
		response.setHttpStatus(HttpStatus.FOUND);
		response.setErrorCode(0);
		response.setObjects(records);
		return response;
	}

	@PostMapping("/addRecord")
	public Response addRecord(@RequestBody Ledger record) {
		Response response = new Response();
		if(record == null) {
			response.setIsSuccessful(Boolean.FALSE);
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setErrorCode(HttpStatus.BAD_REQUEST.value());
			response.setObjects(null);
			return response;
		}
		response.setIsSuccessful(Boolean.TRUE);
		int recordId = ledgerService.addRecord(record);
		if(recordId > 0) {
			Map<String, Integer> recordMap = new HashMap<>();
			List<Map<String, Integer>> recordList = new ArrayList<>();
			recordMap.put("RecordId", recordId);
			recordList.add(recordMap);
			response.setIsSuccessful(Boolean.TRUE);
			response.setHttpStatus(HttpStatus.OK);
			response.setErrorCode(0);
			response.setObjects(recordList);
		}else {
			response.setIsSuccessful(Boolean.FALSE);
			response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setObjects(null);
		}
		return response;
	}

	@DeleteMapping("/deleteRecord")
	public Response deleteRecord(@RequestParam("id") int id) {
		Response response = new Response();
		response.setIsSuccessful(Boolean.FALSE);
		response.setHttpStatus(HttpStatus.FORBIDDEN);
		response.setErrorCode(HttpStatus.FORBIDDEN.value());
		response.setObjects(null);
		return response;
	}
}
