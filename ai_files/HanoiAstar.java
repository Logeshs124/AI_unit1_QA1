import java.util.*;

public class HanoiAStar {

    static class State {
        List<List<Integer>> pegs;

        State(List<List<Integer>> pegs) {
            this.pegs = pegs;
        }

        boolean isGoal() {
            return pegs.get(0).isEmpty() && pegs.get(1).isEmpty() && pegs.get(2).size() == 3;
        }

        int heuristic() {
            return 3 - pegs.get(2).size();  // disks not on goal peg
        }

        State copy() {
            List<List<Integer>> newPegs = new ArrayList<>();
            for (List<Integer> peg : pegs) {
                newPegs.add(new ArrayList<>(peg));
            }
            return new State(newPegs);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof State && pegs.equals(((State)obj).pegs);
        }

        @Override
        public int hashCode() {
            return pegs.hashCode();
        }

        @Override
        public String toString() {
            return pegs.toString();
        }
    }

    static class Node implements Comparable<Node> {
        State state;
        List<State> path;
        int cost;

        Node(State s, List<State> p, int cost) {
            this.state = s;
            this.path = p;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return (this.cost + this.state.heuristic()) - (other.cost + other.state.heuristic());
        }
    }

    public static void aStar(State start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<State> visited = new HashSet<>();

        pq.add(new Node(start, List.of(start), 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            State current = node.state;

            if (visited.contains(current)) continue;
            visited.add(current);

            if (current.isGoal()) {
                System.out.println("A* Solution:");
                for (State s : node.path) System.out.println(s);
                return;
            }

            for (int from = 0; from < 3; from++) {
                if (current.pegs.get(from).isEmpty()) continue;

                int disk = current.pegs.get(from).get(current.pegs.get(from).size() - 1);

                for (int to = 0; to < 3; to++) {
                    if (from == to) continue;

                    if (!current.pegs.get(to).isEmpty() &&
                        current.pegs.get(to).get(current.pegs.get(to).size() - 1) < disk)
                        continue;

                    State next = current.copy();
                    next.pegs.get(from).remove(next.pegs.get(from).size() - 1);
                    next.pegs.get(to).add(disk);

                    List<State> newPath = new ArrayList<>(node.path);
                    newPath.add(next);

                    pq.add(new Node(next, newPath, node.cost + 1));
                }
            }
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> pegs = Arrays.asList(
                new ArrayList<>(Arrays.asList(3, 2, 1)),
                new ArrayList<>(),
                new ArrayList<>()
        );

        aStar(new State(pegs));
    }
}
