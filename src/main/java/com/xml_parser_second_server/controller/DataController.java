package com.xml_parser_second_server.controller;

import com.xml_parser_second_server.model.DataFromTransfer;
import com.xml_parser_second_server.repository.OrganizationRepository;
import com.xml_parser_second_server.repository.ReportDocRepository;
import com.xml_parser_second_server.service.InputDataHandler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DataController {

    private final InputDataHandler inputDataHandler;


    private final OrganizationRepository organizationRepository;

    private final ReportDocRepository reportDocRepository;

    public DataController(InputDataHandler inputDataHandler, OrganizationRepository organizationRepository, ReportDocRepository reportDocRepository) {
        this.inputDataHandler = inputDataHandler;
        this.organizationRepository = organizationRepository;
        this.reportDocRepository = reportDocRepository;
    }

    @PostMapping(value = "/getData", consumes = MediaType.APPLICATION_XML_VALUE)
    public void acceptData(@RequestBody DataFromTransfer dataFromTransfer) {
        inputDataHandler.parseInputData(dataFromTransfer);
    }

    @GetMapping(value = {"/check/{id}", "/check/org/{organizationName}", "/check"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getStat(@PathVariable(required = false) Long id,
                                  @PathVariable(required = false) String organizationName) {
        if (id != null) {
            return this.organizationRepository
                    .findById(id)
                    .map(ResponseEntity.accepted()::body)
                    .orElse(ResponseEntity.notFound().build());
        }
        if (organizationName != null) {
            return ResponseEntity.ok(organizationRepository.findByInfPayCnamePayOrInfRcpCnamePay
                    (organizationName, organizationName));
        } else {
            return ResponseEntity.ok(inputDataHandler.findAllOrganizations());
        }
    }

    @GetMapping(value = "/average", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity stat() {
        List avgSumOfDocsAndCountOfDocs = reportDocRepository.getAvgSumOfDocsAndCountOfDocs();
        return ResponseEntity.ok(avgSumOfDocsAndCountOfDocs);
    }
}
