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
        int maxX = data.size();
        int maxY = data.get(0).length();
        List<Edge>[] edges = buildEdges(data);
        return dijkstra(edges, 0, maxX * maxY - 1);
    }

    static int part2(List<String> data) {
        return 0;
    }

    private static int dijkstra(List<Edge>[] edges, int startVertexId, int endVertexId) {
        int n = edges.length;
        int[] dist = new int[n];
        PriorityQueue<VertexRisk> pq = new PriorityQueue<>(n, Comparator.comparingInt(e -> e.risk));

        for (int i = 0; i < n; i++) {
            dist[i] = i == startVertexId ? 0 : Integer.MAX_VALUE;
            pq.add(new VertexRisk(i, dist[i]));
        }

        while (pq.size() > 0) {
            VertexRisk vr = pq.poll();
            int u = vr.vertex;

            if (dist[u] == Integer.MAX_VALUE || dist[u] < vr.risk) {
                continue;
            }

            for (Edge edge : edges[u]) {
                int v = edge.target;
                int risk = edge.risk;

                if (dist[v] > dist[u] + risk) {
                    dist[v] = dist[u] + risk;
                    pq.add(new VertexRisk(v, dist[v]));
                }
            }
        }

        return dist[endVertexId];
    }

    private static List<Edge>[] buildEdges(List<String> data) {

        int maxX = data.size();
        int maxY = data.get(0).length();

        List<Edge>[] edges = new List[maxX * maxY];

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                int vertexId = y * maxX + x;
                edges[vertexId] = new ArrayList<>();

                if (x > 0) {
                    int cost = Integer.parseInt(data.get(y), x - 1, x, 10);
                    edges[vertexId].add(new Edge(vertexId, vertexId - 1, cost));
                }

                if (x < maxX - 1) {
                    int cost = Integer.parseInt(data.get(y), x + 1, x + 2, 10);
                    edges[vertexId].add(new Edge(vertexId, vertexId + 1, cost));
                }

                if (y > 0) {
                    int cost = Integer.parseInt(data.get(y - 1), x, x + 1, 10);
                    edges[vertexId].add(new Edge(vertexId, (y - 1) * maxX + x, cost));
                }

                if (y < maxY - 1) {
                    int cost = Integer.parseInt(data.get(y + 1), x, x + 1, 10);
                    edges[vertexId].add(new Edge(vertexId, (y + 1) * maxX + x, cost));
                }
            }
        }

        return edges;
    }

    private static class VertexRisk {
        private final int vertex;
        private final int risk;

        private VertexRisk(int vertex, int risk) {
            this.vertex = vertex;
            this.risk = risk;
        }
    }

    private static class Edge {
        private final int source;
        private final int target;
        private final int risk;

        private Edge(int source, int target, int risk) {
            this.source = source;
            this.target = target;
            this.risk = risk;
        }
    }
}
