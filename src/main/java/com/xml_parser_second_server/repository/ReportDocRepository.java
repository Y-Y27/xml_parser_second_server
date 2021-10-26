package com.xml_parser_second_server.repository;

import com.xml_parser_second_server.model.DataFromTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDocRepository extends JpaRepository<DataFromTransfer.InnerDataList.ReportDoc, Long> {

    @Query(value = "SELECT AVG (sum_of_docs) as average_sum, COUNT (*) as number_of_docs " +
            "FROM ( " +
            "SELECT amount_out, SUM (amount_out) as sum_of_docs " +
            "FROM report_doc " +
            "GROUP BY amount_out) as innerquery",
            nativeQuery = true)
    List getAvgSumOfDocsAndCountOfDocs();
}
