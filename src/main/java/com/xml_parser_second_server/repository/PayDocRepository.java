package com.xml_parser_second_server.repository;

import com.xml_parser_second_server.model.DataFromTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayDocRepository extends JpaRepository<DataFromTransfer.InnerDataList.payDoc, Long> {

    @Query("select count (p) from pay_doc p " +
            "join Organization o ON p.organization = o.id " +
            "where o.infPayCnamePay = :infPayCnamePay")
    Long checkStatPay(@Param(value = "infPayCnamePay") String infPayCnamePay);

    @Query("select count (p) AS pay from pay_doc p " +
            "join Organization o ON p.organization = o.id " +
            "where o.infRcpCnamePay = :infRcpCnamePay")
    Long checkStatRcp(@Param(value = "infRcpCnamePay") String infRcpCnamePay);
}
