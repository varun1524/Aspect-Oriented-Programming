package edu.sjsu.cmpe275.aop.aspect;

import edu.sjsu.cmpe275.aop.TweetService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;
import java.io.IOException;

@Aspect
@Order(1)
public class RetryAspect {

    @Autowired TweetService tweetService;

    int noOfRetries = 0;
    boolean aspectResult = false;


    /*
    * In case of IOException 3 tries will be made to execute method successfully
    * Otherwise Exception handled
    * */
    @Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
    public void retryAdvice(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("Prior to the executuion of the method %s\n", methodName);
        Object result = null;
        try {
            aspectResult = false;
            result = joinPoint.proceed();
            aspectResult = true;
            System.out.printf("Finished the executuion of the method %s with result %s\n", methodName, result);
        }
        catch (IOException ex){
            Object[] obj = joinPoint.getArgs();
            System.out.println("It is in IOException");
            if (methodName.equals("tweet")) {
                String user = obj[0].toString();
                String message = obj[1].toString();
                System.out.println("Exception in Tweet method");
                System.out.println(user);
                System.out.println(message);
                if(noOfRetries < 3){
                    noOfRetries++;
                    try{
                        tweetService.tweet(user, message);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    noOfRetries=0;
                    aspectResult = false;
                    ex.printStackTrace();
                }
            }
            else if (methodName.equals("follow")) {
                String follower = obj[0].toString();
                String followee = obj[1].toString();
                System.out.println("Exception in follow method");
                if(noOfRetries < 3){
                    noOfRetries++;
                    try{
                        tweetService.follow(follower, followee);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    noOfRetries=0;
                    aspectResult = false;
                    ex.printStackTrace();
                }
            }
            else if (methodName.equals("block")) {
                String user = obj[0].toString();
                String blockedUser = obj[1].toString();
                System.out.println("Exception in block method");
                if(noOfRetries < 3){
                    noOfRetries++;
                    try{
                        tweetService.block(user, blockedUser);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    noOfRetries=0;
                    aspectResult = false;
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.printf("Aborted the executuion of the method %s\n", joinPoint.getSignature().getName());
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

	/*@AfterThrowing(pointcut = "execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))", throwing = "e")
	public void dummyAdvice(JoinPoint joinPoint, IOException e) throws Throwable{
		String methodName = joinPoint.getSignature().getName();
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
					if(noOfRetries < 3){
						noOfRetries++;
						tweetService.tweet(user, message);
					}
					else{
						noOfRetries=0;
					}
				}
				else if (methodName.equals("follow")) {
					String follower = obj[0].toString();
					String followee = obj[1].toString();
					System.out.println("Exception in follow");
					if(noOfRetries < 3){
						noOfRetries++;
						tweetService.follow(follower, followee);
					}
					else{
						noOfRetries=0;
					}
				}
				else if (methodName.equals("block")) {
					String user = obj[0].toString();
					String blockedUser = obj[1].toString();
					System.out.println("Exception in block");
					if(noOfRetries < 3){
						noOfRetries++;
						tweetService.block(user, blockedUser);
					}
					else{
						noOfRetries=0;
					}
				}
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
			System.out.printf("Aborted the executuion of the method %s\n", joinPoint.getSignature().getName());
		}
	}*/
}
