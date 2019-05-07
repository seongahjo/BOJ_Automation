package com.skel.algo.auto.bean;

import com.skel.algo.auto.question.Q14890;
import com.skel.algo.auto.question.Question;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MainSetting {

    static final String BACKJOONURL = "https://www.acmicpc.net/problem/";

    @Bean
    public void getTest() {
        Question q = new Q14890();
        String className = q.getClass().getName();
        String number = className.split(".Q")[1];
        List<String> inputs = Collections.emptyList();
        List<String> outputs = Collections.emptyList();
        try {
            Document doc = Jsoup.connect(BACKJOONURL + number).userAgent("Mozila").get();
            Elements elements = doc.getElementsByTag("section");
            inputs = elements.stream().filter(s -> s.id().startsWith("sampleinput")).map(Element::text).map(s -> s.split("복사 ")[1]).collect(Collectors.toList());
            outputs = elements.stream().filter(s -> s.id().startsWith("sampleoutput")).map(Element::text).map(s -> s.split("복사 ")[1]).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("error ", e);
        }
        boolean isSuccess = true;
        for (int i = 0; i < inputs.size(); i++) {
            String output = q.run(inputs.get(i));
            boolean result = output.equals(outputs.get(i));
            if (isSuccess)
                isSuccess = result;
            log.info("index {} , expected : {}, actual : {}, result : {} ", i + 1, outputs.get(i), result, result);
        }
        if (isSuccess) {
            log.info("ALL SUCCEED");
            File file = new File("src/main/java/com/skel/algo/auto/question/" + className.split("\\.")[5] + ".java");
            StringBuilder sb = new StringBuilder();
            try (BufferedReader fi = new BufferedReader(new FileReader(file))) {
                String line = "";
                while ((line = fi.readLine()) != null) {
                    if (line.contains("public class"))
                        line = "public class main {";
                    if (line.contains("String run"))
                        line = "\t public static void main(String[] args){";
                    if (!line.contains("package") && !line.contains("@Override")) {
                        sb.append(line);
                    }
                    sb.append("\n");
                }
            } catch (IOException e) {
                log.error("error ", e);
            }
            log.info("test {}", sb.toString());
        } else
            log.info("FAILED");


    }
}
