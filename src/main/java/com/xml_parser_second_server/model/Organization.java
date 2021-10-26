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

    public Organization(String InfPayInnPay, String InfPayKppPay, String InfPayCnamePay, String InfRcpInnPay, String InfRcpKppPay, String InfRcpCnamePay) {
        this.infPayInnPay = InfPayInnPay;
        this.infPayKppPay = InfPayKppPay;
        this.infPayCnamePay = InfPayCnamePay;
        this.infRcpInnPay = InfRcpInnPay;
        this.infRcpKppPay = InfRcpKppPay;
        this.infRcpCnamePay = InfRcpCnamePay;
    }

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
