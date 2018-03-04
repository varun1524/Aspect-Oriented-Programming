package edu.sjsu.cmpe275.aop.aspect;

import edu.sjsu.cmpe275.aop.TweetServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

import java.io.IOException;
import java.net.ServerSocket;

@Aspect
@Order(1)
public class RetryAspect {

//	@Autowired TweetServiceImpl tweetService;

	/*@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
	public void dummyAdvice(ProceedingJoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.printf("Prior to the executuion of the method %s\n", methodName);
		Object result = null;
		try {
			result = joinPoint.proceed();
			System.out.printf("Finished the executuion of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
			*//*if(e.equals("IOException")){
				if(methodName.equals("tweet")){
					System.out.println("Exception in Tweet");
				}
				else if(methodName.equals("follow")){

				}
				else if(methodName.equals("block")){

				}
			}*//*
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.printf("Aborted the executuion of the method %s\n", joinPoint.getSignature().getName());
		}
		catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}*/

	@AfterThrowing(pointcut = "execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))", throwing = "e")
	public void dummyAdvice(JoinPoint joinPoint, IOException e){
		String methodName = joinPoint.getSignature().getName();
		int noOfRetries = 0;
		System.out.printf("After exception in the method %s\n", methodName);
		try {
			if (e instanceof IOException) {
				Object[] obj = joinPoint.getArgs();
				System.out.println("It is in IOException");
				if (methodName.equals("tweet")) {
					String user = obj[0].toString();
					String message = obj[1].toString();
					System.out.println("Exception in Tweet");
					System.out.println(user);
					System.out.println(message);
					while(noOfRetries < 3){
						tweetService.tweet(user, message);
						noOfRetries++;
					}
				}
				else if (methodName.equals("follow")) {
					String val1 = obj[0].toString();
					String val2 = obj[1].toString();
					System.out.println("Exception in follow");
					while(noOfRetries < 3){
						noOfRetries++;
					}
				}
				else if (methodName.equals("block")) {
					String val1 = obj[0].toString();
					String val2 = obj[1].toString();
					System.out.println("Exception in block");
					while(noOfRetries < 3){
						noOfRetries++;
					}
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.out.printf("Aborted the executuion of the method %s\n", joinPoint.getSignature().getName());
		} catch (Throwable throwable) {
			System.out.printf("Throwable Aborted the executuion of the method %s\n", joinPoint.getSignature().getName());
			throwable.printStackTrace();
		}
	}
}
