package com.example.legendary.config.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 注意，重写JobFactory类的作用
 * 这个配置主要用于QuartzConfigration，
 * 在QuartzConfigration配置中需要注入这个文件，
 * 这个文件主要是注入AutowireCapableBeanFactory 这个类，
 * 不然你在实现类中是没办法注入其他service或者其他东西
 * @Author: 吴嘉晟
 * @Date: 2019/8/9 18:45
 * @Version 1.0
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    private final AutowireCapableBeanFactory capableBeanFactory;

    @Autowired
    public JobFactory(AutowireCapableBeanFactory capableBeanFactory) {
        this.capableBeanFactory = capableBeanFactory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
