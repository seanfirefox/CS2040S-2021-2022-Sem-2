/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {
    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };
    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        int len = dataArray.length - 1;
        int begin = 0;
        if (dataArray.length == 0) {
            throw new RuntimeException("array is empty");
        } else if (len == 0) { // when array size is 1
            return dataArray[len];
        } else if (len == 1) { // when array size is 2
            return Math.max(dataArray[0], dataArray[1]);
        } else if (dataArray[0] > dataArray[1]) { // when array decreases at the start
            return Math.max(dataArray[len], dataArray[0]);
        } else {
            while (begin <= len) { // when array increases at the start
                int mid = begin + (len - begin) / 2;
                if (begin == len) {
                    return dataArray[len];
                } else if (len - begin == 1) {
                    return Math.max(dataArray[len], dataArray[begin]);
                } else if (dataArray[mid] > dataArray[mid + 1] && dataArray[mid] > dataArray[mid - 1]){
                    return dataArray[mid];
                } else if (dataArray[mid + 1] > dataArray[mid]) {
                    begin = mid + 1;
                } else {
                    len = mid - 1;
                }
            }
        }
        return dataArray[begin];
    }
    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
