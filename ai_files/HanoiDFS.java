import java.util.*;

public class HanoiDFS {

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

    static void dfs(State state, Set<State> visited, List<State> path) {
        if (visited.contains(state)) return;

        visited.add(state);
        path.add(state);

        if (state.isGoal()) {
            System.out.println("DFS Solution:");
            for (State s : path) System.out.println(s);
            System.exit(0);
        }

        // Try all moves
        for (int from = 0; from < 3; from++) {
            if (state.pegs.get(from).isEmpty()) continue;

            int disk = state.pegs.get(from).get(state.pegs.get(from).size() - 1);

            for (int to = 0; to < 3; to++) {
                if (from == to) continue;

                if (!state.pegs.get(to).isEmpty() &&
                    state.pegs.get(to).get(state.pegs.get(to).size() - 1) < disk)
                    continue;

                State next = state.copy();
                next.pegs.get(from).remove(next.pegs.get(from).size() - 1);
                next.pegs.get(to).add(disk);

                dfs(next, visited, path);
            }
        }

        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        List<List<Integer>> pegs = Arrays.asList(
                new ArrayList<>(Arrays.asList(3, 2, 1)),
                new ArrayList<>(),
                new ArrayList<>()
        );

        dfs(new State(pegs), new HashSet<>(), new ArrayList<>());
    }
}
