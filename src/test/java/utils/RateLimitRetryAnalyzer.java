package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RateLimitRetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private static final int MAX_RETRY = 5; // era 3

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRY) {
            count++;
            try {
                Thread.sleep(4000); // era 500
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return true;
        }
        return false;
    }
}