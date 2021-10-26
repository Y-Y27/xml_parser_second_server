package com.xml_parser_second_server;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class XmlParserSecondServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(XmlParserSecondServerApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
