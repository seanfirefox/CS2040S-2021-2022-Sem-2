import java.util.HashSet;

import java.util.ArrayList;

public class TSPGraph implements IApproximateTSP {

    @Override

    public void MST(TSPMap map) {

        // TODO: implement this method

        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();

        pq.add(0, 0.0);

        for (int i = 1; i < map.getCount(); i++) {

            pq.add(i, Double.POSITIVE_INFINITY);

        }

        HashSet<Integer> set = new HashSet<>();

        while (!pq.isEmpty()) {

            int currkey = pq.extractMin();

            set.add(currkey);

            for (int key = 0; key < map.getCount(); key++) {

                double weight = map.pointDistance(key, currkey);

                if (!(key == currkey || set.contains(key)) && weight < pq.lookup(key)) {

                    pq.decreasePriority(key, weight);

                    map.setLink(key, currkey, false);

                }

            }

        }

        map.redraw();

    }

    @Override

    public void TSP(TSPMap map) {

        MST(map);

        // TODO: implement the rest of this method.

        ArrayList<Integer> arr = new ArrayList<>();

        arr.add(0);

        class DFS {

            public void dfs(TSPMap map, int index) {

                for (int i = 0; i < map.getCount(); i++) {

                    if (map.getLink(i) == index) {

                        arr.add(i);

                        dfs(map, i);

                    }

                }

            }

        }

        for (int i = 0; i < map.getCount() - 1; i++) {

            if (map.getLink(i) == 0) {

                arr.add(i);

                new DFS().dfs(map, i);

            }

        }

        for (int i = 0; i < arr.size() - 1; i++) {

            map.setLink(arr.get(i), arr.get(i + 1), false);

        }

        map.setLink(arr.get(arr.size() - 1), 0, false);

        map.redraw();

    }

    @Override

    public boolean isValidTour(TSPMap map) {

        // Note: this function should with with *any* map, and not just results from TSP().

        // TODO: implement this method

        if (map != null) {

            if (map.getCount() == 1) {

                return true;

            }

            int currpoint = 0;

            boolean validTour = true;

            boolean[] visited = new boolean[map.getCount()];

            for (int i = 0; i < map.getCount(); i++) {

                currpoint = map.getPoint(currpoint).getLink();

                if (currpoint == -1 || visited[currpoint]) {

                    validTour = validTour && false;

                    break;

                }

                visited[currpoint] = true;

            }

            return validTour && currpoint == 0;

        } else {

            return false;

        }

    }

    @Override

    public double tourDistance(TSPMap map) {

        // Note: this function should with with *any* map, and not just results from TSP().

        // TODO: implement this method

        if (isValidTour(map)) {

            double distance = 0;

            int currPoint = 0;

            int nextPoint;

            for (int i = 0; i < map.getCount(); i++) {

                nextPoint = map.getPoint(currPoint).getLink();

                distance += map.pointDistance(currPoint, nextPoint);

                currPoint = nextPoint;

            }

            return distance;

        }

        return -1;

    }

    public static void main(String[] args) {

        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");

        TSPGraph graph = new TSPGraph();

        // graph.MST(map);

        graph.TSP(map);

        System.out.println(graph.isValidTour(map));

        System.out.println(graph.tourDistance(map));

    }

}

