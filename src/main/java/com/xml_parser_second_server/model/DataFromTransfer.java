package com.xml_parser_second_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
public class DataFromTransfer {

    private List<InnerDataList> innerDataList;

    @Data
    @NoArgsConstructor
    public static class InnerDataList {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private ReportDoc reportDoc;

        private payDoc payDoc;

        @Data
        @NoArgsConstructor
        @Entity
        public static class ReportDoc {

            @Id
            private Long id;

            @OneToOne(fetch = FetchType.LAZY)
            @MapsId
            @JoinColumn(name = "id")
            private payDoc payDoc;

            private String DocNum;

            private String DocDate;

            private String DocGUID;

            private String OperType;

            private Double AmountOut;
        }

        @NoArgsConstructor
        @Data
        @Entity(name = "pay_doc")
        @Table(name = "pay_doc")
        public static class payDoc {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
            @JoinColumn(name = "bank_id")
            @JsonIgnore
            private Bank bank;

            @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
            @JoinColumn(name = "organization")
            @JsonIgnore
            private Organization organization;

            @OneToOne(cascade = CascadeType.ALL)
            @MapsId
            @JoinColumn(name = "id")
            private InnerDataList.payDoc.infPay infPay;

            @OneToOne(cascade = CascadeType.ALL)
            @MapsId
            @JoinColumn(name = "id")
            private InnerDataList.payDoc.bankPay bankPay;

            @OneToOne(cascade = CascadeType.ALL)
            @MapsId
            @JoinColumn(name = "id")
            private InnerDataList.payDoc.infRcp infRcp;

            @OneToOne(cascade = CascadeType.ALL)
            @MapsId
            @JoinColumn(name = "id")
            private InnerDataList.payDoc.bankRCP bankRCP;

            private String Purpose;

            @NoArgsConstructor
            @Data
            @Entity
            @Table(name = "Inf_PAY")
            public static class infPay {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                private String INN_PAY;

                private String KPP_PAY;

                private String CName_PAY;
            }

            @NoArgsConstructor
            @Data
            @Entity
            @Table(name = "Bank_PAY")
            public static class bankPay {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                private String BS_PAY;

                private String BIC_PAY;

                private String BS_KS_PAY;
            }

            @NoArgsConstructor
            @Data
            @Entity
            @Table(name = "Inf_RCP")
            public static class infRcp {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                private String INN_PAY;

                private String KPP_PAY;

                private String CName_PAY;
            }

            @NoArgsConstructor
            @Data
            @Entity
            @Table(name = "Bank_RCP")
            public static class bankRCP {

                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                private String BS_PAY;

                private String BIC_PAY;

                private String BS_KS_PAY;
            }
        }
    }
}

