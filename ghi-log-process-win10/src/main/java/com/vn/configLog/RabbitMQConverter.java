package com.vn.configLog;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RabbitMQConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
//        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
//        if (attrs != null) {
//            return attrs.getSessionId();
//        }
        return "ADMIN";
    }
}
