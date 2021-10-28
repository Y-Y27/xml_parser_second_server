package com.xml_parser_second_server.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "organization")
    private List<DataFromTransfer.InnerDataList.payDoc> payDocs;

    private String infPayInnPay;

    private String infPayKppPay;

    private String infPayCnamePay;

    private String infRcpInnPay;

    private String infRcpKppPay;

    private String infRcpCnamePay;

    @Override
    public String toString() {
        return "Organization{" +
                "infPayInnPay='" + infPayInnPay + '\'' +
                ", infPayKppPay='" + infPayKppPay + '\'' +
                ", infPayCnamePay='" + infPayCnamePay + '\'' +
                ", infRcpInnPay='" + infRcpInnPay + '\'' +
                ", infRcpKppPay='" + infRcpKppPay + '\'' +
                ", infRcpCnamePay='" + infRcpCnamePay + '\'' +
                '}';
    }


}
