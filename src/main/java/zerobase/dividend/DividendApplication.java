package zerobase.dividend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class DividendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendApplication.class, args);

        /* 스크래핑 코드
        try {
            Connection connection = Jsoup.connect("https://finance.yahoo.com/quote/COKE/history/?frequency=1mo&period1=99153000&period2=1719705600");
            Document document = connection.get();

            // 테이블 태그가 강의 시점과 달라져서 class로 가져옴
            //document.getElementsByAttributeValue("data-test", "hsitorical-prices");
            Elements eles = document.getElementsByAttributeValue("class", "table svelte-ewueuo");
            Element ele = eles.get(0);
            //System.out.println(ele);

            // 0 : thead
            // 1 : tbody
            // 2 : tfoot
            Element tbody = ele.children().get(1);
            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }
                //System.out.println(txt);

                String[] splits = txt.split(" ");
                String month = splits[0];
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                System.out.println(year + "/" + month + "/" + day + "->" + dividend);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        */

        /*
        //YahooFinanceScraper scraper = new YahooFinanceScraper();
        Scraper scraper = new YahooFinanceScraper();
        //var result = scraper.scrap(Company.builder().ticker("O").build()); // 배당금
        var result = scraper.scrapCompanyByTicker("COKE"); // 회사명
        System.out.println(result);
        */

        /*
        Trie trie = new PatriciaTrie();
        AutoComplete autoComplete = new AutoComplete(trie);
        AutoComplete autoComplete1 = new AutoComplete(trie);

        autoComplete.add("hello");

        System.out.println(autoComplete.get("hello"));
        System.out.println(autoComplete1.get("hello"));
        */

        //System.out.println("Main -> " + Thread.currentThread().getName());
    }

}
