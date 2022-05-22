public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        // TODO: implement this
        int somenum;
        KeyValuePair[] arr = new KeyValuePair[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new KeyValuePair(size - i, i);
        }
        sorter.sort(arr);
        boolean check = true;
        for (int i = 0; i < size - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) == 1){
                check = false;
            }
        }
        return check;
    }

    public static boolean isStable(ISort sorter, int size) {
        // TODO: implement this
        KeyValuePair[] arr = new KeyValuePair[size];
        // array tested here has 2 different repeated values
        for (int i = 0; i < size; i++) {
            arr[i] = new KeyValuePair(10, i);
        }
        arr[size / 2] = new KeyValuePair(15, arr[size/2].getValue());
        arr[0] = new KeyValuePair(15, arr[0].getValue());
        sorter.sort(arr);
        boolean check = true;
        for (int i = 0; i < size - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) == 0) {
                if (arr[i].getValue() > arr[i + 1].getValue()) {
                    check = false;
                    break;
                }
            }
        }
        return check;
    }

    public static void main(String[] args) {
        // TODO: implement this
        ISort sorterA = new SorterA();
        ISort sorterB = new SorterB();
        ISort sorterC = new SorterC();
        ISort sorterD = new SorterD();
        ISort sorterE = new SorterE();
        ISort sorterF = new SorterF();

        /*
        for (int i = 2; i < 10000; i++) {
            if (!(SortingTester.checkSort(sorterA, i) && SortingTester.checkSort(sorterB, i) &&
                    SortingTester.checkSort(sorterC, i) && SortingTester.checkSort(sorterD, i) &&
            SortingTester.checkSort(sorterE, i) && SortingTester.checkSort(sorterF, i))) {
                System.out.println(i);
                break;
            }
        }
        */
        // output: 2
        // used to test at which array size does Dr Evil not sort, no point testing 1
        System.out.println("Checksort output:");
        System.out.println("A sorted is " + SortingTester.checkSort(sorterA, 2));
        System.out.println("B sorted is " + SortingTester.checkSort(sorterB, 2));
        System.out.println("C sorted is " + SortingTester.checkSort(sorterC, 2));
        System.out.println("D sorted is " + SortingTester.checkSort(sorterD, 2));
        System.out.println("E sorted is " + SortingTester.checkSort(sorterE, 2));
        System.out.println("F sorted is " + SortingTester.checkSort(sorterF, 2));

        // output: A sorted is true
        //         B sorted is false
        //         C sorted is true
        //         D sorted is true
        //         E sorted is true
        //         F sorted is true
        //         SorterB is Dr.Evil

        System.out.println("---------------------------------");
        System.out.println("isStable output:");
        System.out.println("A stable is " + SortingTester.isStable(sorterA, 100));
        System.out.println("C stable is " + SortingTester.isStable(sorterC, 100));
        System.out.println("D stable is " + SortingTester.isStable(sorterD, 100));
        System.out.println("E stable is " + SortingTester.isStable(sorterE, 100));
        System.out.println("F stable is " + SortingTester.isStable(sorterF, 100));

        // output:  A stable is true
        //          C stable is true
        //          D stable is false
        //          E stable is false
        //          F stable is true
        // SorterD and SorterE are unstable, so they are Quicksort or Selection sort.


        KeyValuePair[] arr = new KeyValuePair[10000];
        for(int i = 0; i < 10000; i++){
             arr[i] = new KeyValuePair(10000 - i, i);
        }

        System.out.println("-------------------");

/*
        StopWatch stopWatchA = new StopWatch();
        stopWatchA.start();
        sorterA.sort(arr);
        stopWatchA.stop();
        System.out.println("Time of A: " + stopWatchA.getTime());
        // 0.0292s
*/
/*
        StopWatch stopWatchC = new StopWatch();
        stopWatchC.start();
        sorterC.sort(arr);
        stopWatchC.stop();
        System.out.println("Time of C: " + stopWatchC.getTime());
        // output: 31.14s
*/

/*
        StopWatch stopWatchD = new StopWatch();
        stopWatchD.start();
        sorterD.sort(arr);
        stopWatchD.stop();
        System.out.println("Time of D: " + stopWatchD.getTime());
        // 0.1797s
*/
/*
        StopWatch stopWatchE = new StopWatch();
        stopWatchE.start();
        sorterE.sort(arr);
        stopWatchE.stop();
        System.out.println("Time of E: " + stopWatchE.getTime());
        // 0.156s
*/
/*
        StopWatch stopWatchF = new StopWatch();
        stopWatchF.start();
        sorterF.sort(arr);
        stopWatchF.stop();
        System.out.println("Time of F: " + stopWatchF.getTime());
        // 0.418s
*/

    }
}
