package top.zhacker.testdriven.xp.chapter5;

import java.util.Arrays;


/**
 * Created by zhacker.
 * Time 2017/5/29 下午6:57
 * Desc 文件描述
 */
public class PrimeGenerator {
    
    public static int[] generatePrimes(int max) {
        if(max<2){
            return new int[0];
        }
        boolean[] isSieved = initSieve(max+1);
        sieve(isSieved);
        return loadPrimes(isSieved);
    }
    
    
    private static boolean[] initSieve(int maxValue) {
        boolean[] isSieved = new boolean[maxValue];
        
        Arrays.fill(isSieved, true);
        
        isSieved[0] = false;
        isSieved[1] = false;
        return isSieved;
    }
    
    
    private static void sieve(boolean[] isSieved) {
        int maxValue = isSieved.length;
        int limit = determineLimit(maxValue);
        for(int i=2; i< limit; ++i){
            for(int j = 2 * i; j< maxValue; j+=i){
                isSieved[j] = false;
            }
        }
    }
    
    private static int determineLimit(int maxValue){
        return (int) Math.sqrt(maxValue) + 1;
    }
    
    
    private static int[] loadPrimes(boolean[] isSieved) {

        int count = numberOfSieved(isSieved);
        
        int[] primes = new int[count];
        for(int i=0,j=0; i< isSieved.length; ++i){
            if(isSieved[i]){
                primes[j++] = i;
            }
        }
        return primes;
    }
    
    private static int numberOfSieved(boolean[] isSieved){
        int count = 0;
        for (boolean anIsSieved : isSieved) {
            if (anIsSieved) {
                ++count;
            }
        }
        return count;
    }
}
