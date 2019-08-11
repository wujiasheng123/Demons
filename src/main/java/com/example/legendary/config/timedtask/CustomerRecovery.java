/*
package com.example.legendary.config.timedtask;

import com.example.legendary.common.utils.ThreadPoolExecutorUtil;
import com.example.legendary.trunk.client.service.OverviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

*/
/**
 * 回收客户
 * @Author: 吴嘉晟
 * @Date: 2018/11/16 16:34
 * @Version 1.0
 */
/*

@Configuration
public class CustomerRecovery {

    private Logger logger = LogManager.getLogger(CustomerRecovery.class);
    private final OverviewService overviewService;

    @Autowired
    public CustomerRecovery(OverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void customerRecovery() {
        ThreadPoolExecutorUtil.getThreadPool().execute(() -> {
            logger.info("正在清理30天未跟踪客户");
            overviewService.customerRecovery();
        });
    }
}
*/
