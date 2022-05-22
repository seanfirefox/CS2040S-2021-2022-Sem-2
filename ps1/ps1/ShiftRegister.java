///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////
/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    int[] arr;
    int gsize;
    int gtap;
    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        if (tap >= 0 || tap <= size) {
            gsize = size;
            gtap = tap;
            arr = new int[size];
        } else {
            throw new RuntimeException("Invalid tap");
        }
    }
    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        if (gsize != seed.length) {
            throw new RuntimeException("Seed length does not match size provided");
        } else {
            for (int i = 0; i < gsize; i++) {
                if (seed[gsize - 1 - i] != 0 || seed[gsize - 1 - i] != 1) {
                    arr[i] = seed[gsize - 1 - i];
                } else {
                    throw new RuntimeException("Invalid seed");
                }
            }
        }
    }
    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // TODO:
        int returned = arr[0] ^ arr[gsize - gtap - 1];
        for (int i = 0; i < gsize - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[gsize - 1] = returned;
        return arr[gsize - 1];
    }
    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        // TODO:
        int g = 0;
        for  (int i = k - 1; i >= 0; i--) {
            g = g + (int) (shift() * Math.pow(2, i));
        }
        return g;
    }
    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toBinary(int[] array) {
        // TODO:
        return 0;
    }
}