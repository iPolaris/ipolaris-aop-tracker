package com.github.ipolaris.aop.tracker.recorder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.google.gson.Gson;

/**
 * Created by tangtianjiang715 on 16-7-28.
 */
public class DefaultTrackerRecorder implements ITrackerRecorder {
    private static final Logger LOGGER = Logger.getLogger(DefaultTrackerRecorder.class);
    public final static String DATE_TIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss:SSS";
    ThreadLocal<Stack<Pair<String, Long>>> methodRequestStack = new ThreadLocal<Stack<Pair<String, Long>>>(){
        @Override
        protected Stack<Pair<String, Long>> initialValue() {
            return new Stack<Pair<String, Long>>();
        }
    };
    @Override
    public void recordBeforeMethod(JoinPoint joinPoint) {
        Stack<Pair<String, Long>> methodTrack = methodRequestStack.get();
        Long methodStart = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        Pair<String, Long> pair = Pair.of(methodName, methodStart);
        String paramsStr = formatObjectsToString(joinPoint.getArgs());
        methodTrack.push(pair);
        LOGGER.info(String.format("request method %s of %s with params %s at %s", methodName, joinPoint.getTarget().getClass().getName(),
                StringUtils.isBlank(paramsStr) ? "no params" : paramsStr, new SimpleDateFormat(DATE_TIME_FORMAT_DEFAULT).format(new Date(methodStart))));

    }

    @Override
    public void recordAfterMethod(JoinPoint joinPoint, Object retVal) {
        Stack<Pair<String, Long>> methodTrack = methodRequestStack.get();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        Long methodStart = 0L;
        if (methodTrack.size() >= 0){
            Pair<String, Long> pair = methodTrack.pop();
            methodStart = pair.getRight();
        }
        LOGGER.info(String.format("request method %s of %s finish with result [%s] at %s cost %s",
                methodName, joinPoint.getTarget().getClass().getName(),
                (retVal == null && !signature.getReturnType().isPrimitive() ? retVal : (retVal == null ? "void" : formatObjectsToString(retVal))),
                new SimpleDateFormat(DATE_TIME_FORMAT_DEFAULT).format(new Date(System.currentTimeMillis())),
                (methodStart.longValue() == 0) ? "--" : System.currentTimeMillis()-methodStart));
    }

    @Override
    public void recordAfterMethodException(JoinPoint joinPoint, Throwable throwable) {
        Long methodStart = 0L;
        Stack<Pair<String, Long>> methodTrack = methodRequestStack.get();
        if (methodTrack.size() >= 0){
            Pair<String, Long> pair = methodTrack.pop();
            methodStart = pair.getRight();
        }
        LOGGER.info(String.format("request method %s of %s finish at %s cost %s with exception %s",
                joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(),
                new SimpleDateFormat(DATE_TIME_FORMAT_DEFAULT).format(new Date(System.currentTimeMillis())),
                (methodStart.longValue() == 0) ? "--" : System.currentTimeMillis()-methodStart, throwable.getMessage()));
    }

    private String formatObjectsToString(Object... args) {
        if (null == args || args.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (Object object : args) {
            sb.append(new Gson().toJson(object)).append("&");//new Gson 是一个很耗时的操作
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        return sb.toString();
    }
}
