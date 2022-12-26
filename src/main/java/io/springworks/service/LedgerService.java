package io.springworks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.springworks.controller.ControllerConstants;
import io.springworks.models.Ledger;
import io.springworks.repos.LedgerRepo;

@Service
public class LedgerService {

	private static Logger logger = LoggerFactory.getLogger(LedgerService.class);

	@Autowired
	private LedgerRepo ledgerRepo;

	private int ledgerId = 000;

	public List<Ledger> getRecords(){
		List<Ledger> records = new ArrayList<>();
		records = ledgerRepo.findAll();
		return records;
	}

	public Ledger getRecord(int id) {
		Optional<Ledger> optionalRecord = ledgerRepo.findById(id);
		if(optionalRecord.isPresent()) {
			return optionalRecord.get();
		}
		return null;
	}

	public int addRecord(Ledger record) {
		if(record == null) {
			logger.error("Not valid Ledger object received, aborting!");
			return -1;
		}
		ControllerConstants.ledgerId += 1;
		record.setId(ControllerConstants.ledgerId);
		ledgerRepo.save(record);
		return ControllerConstants.ledgerId;
	}
}
