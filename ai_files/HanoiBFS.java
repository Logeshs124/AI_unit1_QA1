import java.util.*;

public class HanoiBFS {

    static class State {
        List<List<Integer>> pegs;

        State(List<List<Integer>> pegs) {
            this.pegs = pegs;
        }

        boolean isGoal() {
            return pegs.get(0).isEmpty() && pegs.get(1).isEmpty() && pegs.get(2).size() == 3;
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

    public static void bfs(State start) {
        Queue<List<State>> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.add(List.of(start));

        while (!queue.isEmpty()) {
            List<State> path = queue.poll();
            State current = path.get(path.size() - 1);

            if (visited.contains(current)) continue;
            visited.add(current);

            if (current.isGoal()) {
                System.out.println("BFS Solution:");
                for (State s : path) System.out.println(s);
                return;
            }

            // Expand moves
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

                    List<State> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(newPath);
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

        bfs(new State(pegs));
    }
}
