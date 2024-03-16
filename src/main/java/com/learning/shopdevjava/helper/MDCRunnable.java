package com.learning.shopdevjava.helper;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.Map;

@Slf4j
public abstract class MDCRunnable implements Runnable {
    private Map<String, String> mdcContextMap;

    public MDCRunnable(){
        mdcContextMap = MDC.getCopyOfContextMap();
    }

    abstract void runImpl();

    @Override
    public void run(){
        MDC.setContextMap(mdcContextMap);
        runImpl();
    }

    public static MDCRunnable wrap(Runnable runnable){
        return new MDCRunnable() {
            @Override
            void runImpl() {
                runnable.run();
            }
        };
    }
}
