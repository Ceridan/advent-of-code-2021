package aoc2021;

import java.io.FileNotFoundException;
import java.util.*;

public class Day15 {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> data = Utils.readInputAsStringArray("day15.txt");

        System.out.printf("Day 15, part 1: %d\n", part1(data));
        System.out.printf("Day 15, part 2: %d\n", part2(data));
    }

    static int part1(List<String> data) {
        RisksMap rm = new RisksMap(data, 1);
        return dijkstra(rm, rm.maxX * rm.maxY - 1, 0);
    }

    static int part2(List<String> data) {
        RisksMap rm = new RisksMap(data, 5);
        return dijkstra(rm, rm.maxX * rm.maxY - 1, 0);
    }

    private static int dijkstra(RisksMap rm, int startVertex, int endVertex) {
        int n = rm.maxX * rm.maxY;
        int[] dist = new int[n];
        PriorityQueue<VertexRisk> pq = new PriorityQueue<>(n, Comparator.comparingInt(e -> e.risk));

        for (int i = 0; i < n; i++) {
            dist[i] = i == startVertex ? 0 : Integer.MAX_VALUE;
            pq.add(new VertexRisk(i, dist[i]));
        }

        while (pq.size() > 0) {
            VertexRisk vr = pq.poll();
            int u = vr.vertex;

            if (dist[u] == Integer.MAX_VALUE || dist[u] < vr.risk) {
                continue;
            }

            int risk = rm.getVertexRisk(u);

            for (int v : rm.getNeighbors(u)) {
                if (dist[v] > dist[u] + risk) {
                    dist[v] = dist[u] + risk;
                    pq.add(new VertexRisk(v, dist[v]));
                }
            }
        }

        return dist[endVertex];
    }

    private static class VertexRisk {
        private final int vertex;
        private final int risk;

        private VertexRisk(int vertex, int risk) {
            this.vertex = vertex;
            this.risk = risk;
        }
    }

    private static class RisksMap {
        private final int[][] risksMap;
        private final int maxX;
        private final int maxY;

        public RisksMap(List<String> data, int dimensions) {
            this.risksMap = buildRisksMap(data);
            this.maxY = data.size() * dimensions;
            this.maxX = data.get(0).length() * dimensions;
        }

        public int getVertexRisk(int vertex) {
            int x = vertex % maxY;
            int y = vertex / maxY;
            int baseX = x % this.risksMap[0].length;
            int baseY = y % this.risksMap.length;
            int baseRisk = risksMap[baseY][baseX];

            int tmpRisk = ((baseRisk - 1 + x / this.risksMap[0].length) % 9) + 1;
            return ((tmpRisk - 1 + y / this.risksMap.length) % 9) + 1;
        }

        public List<Integer> getNeighbors(int vertex) {
            List<Integer> vertices = new ArrayList<>();

            if ((vertex % maxX) > 0) {
                vertices.add(vertex - 1);
            }

            if ((vertex % maxX) < maxX - 1) {
                vertices.add(vertex + 1);
            }
            if (vertex - maxX >= 0) {
                vertices.add(vertex - maxX);
            }

            if (vertex + maxX < maxX * maxY) {
                vertices.add(vertex + maxX);
            }

            return vertices;
        }

        private int[][] buildRisksMap(List<String> data) {
            int maxX = data.size();
            int maxY = data.get(0).length();

            int[][] risks = new int[maxY][maxX];

            for (int y = 0; y < maxY; y++) {
                String line = data.get(y);

                for (int x = 0; x < maxX; x++) {
                    risks[y][x] = Integer.parseInt(line, x, x + 1, 10);
                }
            }

            return risks;
        }
    }
}
