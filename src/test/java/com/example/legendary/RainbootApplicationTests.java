package com.example.legendary;

import com.example.legendary.common.utils.HttpClientUtils;
import com.example.legendary.common.utils.ThreadPoolExecutorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RainbootApplicationTests {


    private static final Logger logger = LogManager.getLogger(RainbootApplicationTests.class);

    public static void main(String[] args) {
        for(int i=0; i<1000000; i++) {
            ThreadPoolExecutorUtil.getThreadPool().execute(RainbootApplicationTests::getShop);
        }
    }

    private static void getShop() {
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("type","全部");
        map.add("current","1");
        String result = HttpClientUtils.post("https://www.xunmeme.com/shopStore/searchAll",map);
        System.out.println(result);
    }

}
