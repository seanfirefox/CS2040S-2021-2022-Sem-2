import static org.junit.Assert.*;
import org.junit.Test;
/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {
    /**
     * Returns a shift register to test.
     * @param size
     * @param tap
     * @return a new shift register
     */
    ILFShiftRegister getRegister(int size, int tap){
        return new ShiftRegister(size, tap);
    }
    /**
     * Tests shift with simple example.
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0,1,0,1,1,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {1,1,0,0,0,1,1,1,1,0};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.shift());
        }
    }
    @Test
    public void testShift2() {
        ILFShiftRegister r = getRegister(9, 4);
        int[] seed = {1,1,0,1,0,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {1,1,1,0,1,0,1,0,1,0};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.shift());
        }
    }
    /**
     * Tests generate with simple example.
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0,1,0,1,1,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {6,1,7,2,2,1,6,6,2,3};
        for (int i=0; i<10; i++){
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }
    /**
     * Tests register of length 1.
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = {1};
        r.setSeed(seed);
        int[] expected = {0,0,0,0,0,0,0,0,0,0,};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.generate(3));
        }
    }
    /**
     * Tests tap = 0.
     */
    @Test
    public void testOneTap() {
        ILFShiftRegister r = getRegister(4, 0);
        int[] seed = {1, 0, 0, 1};
        r.setSeed(seed);
        int[] expected = {0, 7, 5, 3};
        for (int i=0; i<4; i++){
            assertEquals(expected[i], r.generate(3));
        }
    }
    /**
     * Tests with erroneous seed.
     * Error handling credits to Eric Goh Kang Yang for the idea to use try and catch instead of assert throw to test
     * validity of error message.
     */
    // It should print out error stating that size does not match length of seed
    @Test
    public void testErrorSize() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = {1,0,0,0,1,1,0};
        try {
            r.setSeed(seed);
            fail("Failed to get an error");
        } catch(RuntimeException re) {
            String message = "Seed length does not match size provided";
            assertEquals(message, re.getMessage());
        }
    }
    // It should print out error stating that tap is invalid (tap is more than size)
    @Test
    public void testErrorTap() {
        try {
            ILFShiftRegister r = getRegister(4, 5);
            int[] seed = {1, 0, 0, 0};
            fail("Failed to get error");
        }  catch(RuntimeException re) {
            String message = "Invalid Tap";
            assertEquals(message, re.getMessage());
        }
    }
    // It should print out error stating that seed is invalid
    // invalid seed(seed contains numbers that are not 1 or 0)
    @Test
    public void testErrorinvalidseed() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = {5, 5, 5, 5};
        try {
            r.setSeed(seed);
            fail("Failed to get error");
        }
        catch(RuntimeException re) {
            String message = "Invalid Seed";
            assertEquals(message, re.getMessage());
        }
    }
}
