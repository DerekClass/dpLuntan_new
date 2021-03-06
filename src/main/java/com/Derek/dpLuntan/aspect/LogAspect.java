package com.Derek.dpLuntan.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.Derek.dpLuntan.controller.Indexcontroller.*(..))")


    void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            sb.append("arg:" + arg.toString() + "|");
        }

        logger.info("before method" + new Date());
    }

    @After("execution(* com.Derek.dpLuntan.controller.Indexcontroller.*(..)))")
    public void afterMethod() {
        logger.info("after method" + new Date());
    }
}

