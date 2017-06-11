package top.zhacker.testdriven.xp.chapter5;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by zhacker.
 * Time 2017/5/29 下午6:56
 * Desc 文件描述
 */
public class TestGeneratePrimes {
    
    @Test
    public void test_primes(){
        int[] nullArray = PrimeGenerator.generatePrimes(0);
        assertEquals(0, nullArray.length);
        
        int[] minArray = PrimeGenerator.generatePrimes(2);
        assertEquals(1, minArray.length);
        assertEquals(2, minArray[0]);
        
        int[] threeArray = PrimeGenerator.generatePrimes(3);
        assertEquals(2, threeArray.length);
        assertEquals(2, threeArray[0]);
        assertEquals(3, threeArray[1]);
        
        int[] centArray = PrimeGenerator.generatePrimes(100);
        assertEquals(25, centArray.length);
        assertEquals(97, centArray[24]);
    }
}
