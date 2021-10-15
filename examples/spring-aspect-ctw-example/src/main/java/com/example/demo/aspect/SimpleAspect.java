package com.example.demo.aspect;

import com.example.demo.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Aspect
@Slf4j
@Configurable
public class SimpleAspect {

  @Autowired private SimpleService simpleService;

  @Pointcut("target(com.example.demo.controller.SimpleController)")
  public void simpleController() {}

  @Pointcut("execution(public * *(..))")
  public void publicMethod() {}

  @Around("simpleController() && publicMethod()")
  public Object execute(ProceedingJoinPoint pjp) throws Throwable {
    log.info("SimpleAspect#execute started");
    simpleService.hello();
    final Object result = pjp.proceed();
    log.info("SimpleAspect#execute finished");
    return result;
  }
}
