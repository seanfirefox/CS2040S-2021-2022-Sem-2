import static org.junit.Assert.*;

import org.junit.Test;

public class LoadBalancingTest {

    /* Tests for Problem 2 */

    @Test
    public void testIsFeasibleLoad1() {
        int[] jobSizes = {1, 3, 5, 7, 9, 11, 10, 8, 6, 4};
        int processors = 10;
        int queryLoad = 10;
        assertEquals(LoadBalancing.isFeasibleLoad(jobSizes, queryLoad, processors), false);
    }

    @Test
    public void testIsFeasibleLoad2() {
        int[] jobSizes = {1, 2, 3};
        int processors = 1;
        int queryLoad = 6;
        assertEquals(LoadBalancing.isFeasibleLoad(jobSizes, queryLoad, processors), true);
    }

    @Test
    public void testFindLoad1() {
        int[] jobSizes = {1, 3, 5, 7, 9, 11, 10, 8, 6, 4};
        int processors = 5;
        assertEquals(LoadBalancing.findLoad(jobSizes, processors), 18);
    }

    @Test
    public void testFindLoad2() {
        int[] jobSizes = {67, 65, 43, 42, 23, 17, 9, 100};
        int processors = 3;
        assertEquals(LoadBalancing.findLoad(jobSizes, processors), 132);
    }

    @Test
    public void testFindLoad3() {
        int[] jobSizes = {4, 100, 80, 15, 20, 25, 30};
        int processors = 2;
        assertEquals(LoadBalancing.findLoad(jobSizes, processors), 170);
    }

    @Test
    public void testFindLoad4() {
        int[] jobSizes = {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83};
        int processors = 8;
        assertEquals(LoadBalancing.findLoad(jobSizes, processors), 261);
    }

    @Test
    public void testFindLoad5() {
        int[] jobSizes = {7};
        int processors = 1;
        assertEquals(LoadBalancing.findLoad(jobSizes, processors), 7);
    }

}
