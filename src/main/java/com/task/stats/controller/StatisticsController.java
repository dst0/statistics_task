package com.task.stats.controller;

import com.task.stats.model.Statistics;
import com.task.stats.model.Transaction;
import com.task.stats.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class StatisticsController {

    @Resource
    private TransactionService transactionService;

    @RequestMapping(
            method = POST,
            value = "/transactions",
            produces = "application/json",
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity addTransaction(@RequestBody Transaction transaction) {
        boolean isTransactionRelevant = transactionService.addTransaction(transaction);

        HttpStatus status = isTransactionRelevant ? HttpStatus.CREATED : HttpStatus.NO_CONTENT;
        return new ResponseEntity(status);
    }

    @RequestMapping(method = GET, value = "/statistics", produces = "application/json")
    public Statistics getStatistics() {
        return transactionService.getStatistics();
    }
}
