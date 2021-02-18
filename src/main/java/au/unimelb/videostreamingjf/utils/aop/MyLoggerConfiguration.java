package au.unimelb.videostreamingjf.utils.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
public class MyLoggerConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MyLoggerConfiguration.class);

    @Before("execution(* au.unimelb.videostreamingjf.controller..*(..))")
    public void traceIp(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        log.info("get request for：{}, from: {}", request.getRequestURL(),request.getRemoteAddr());

    }
}
