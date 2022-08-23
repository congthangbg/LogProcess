package com.vn.configLog;

import ch.qos.logback.classic.PatternLayout;

public class PatternLayoutWithUserContext extends PatternLayout {
    static {
        PatternLayout.defaultConverterMap.put("RabbitMQ", RabbitMQConverter.class.getName());
    }
}
