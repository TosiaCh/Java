public class Main {
    public static void main(String[] args) {
        NumberStatistics numberStats = new NumberStatistics();

        // Ustaw długość boku płaszczyzny
        numberStats.sideLength(10); // Długość boku 10

        // Przykładowa mapa z pozycjami liczb
        Map<Integer, Set<Position>> numberPositions = new HashMap<>();
        Set<Position> positions1 = new HashSet<>();
        positions1.add(new Position(2, 3));
        positions1.add(new Position(4, 5));
        numberPositions.put(1, positions1);

        Set<Position> positions2 = new HashSet<>();
        positions2.add(new Position(7, 8));
        positions2.add(new Position(9, 1));
        numberPositions.put(2, positions2);

        // Dodaj mapę pozycji liczb do NumberStatistics
        numberStats.addNumbers(numberPositions);

        // Test metody neighbours
        Position testPosition = new Position(3, 4); // Przykładowa pozycja do sprawdzenia sąsiadów
        int maxDistanceSquared = 5; // Maksymalny kwadrat odległości

        Map<Integer, Map<Integer, Integer>> result = numberStats.neighbours(testPosition, maxDistanceSquared);

        // Wyświetlenie wyników
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : result.entrySet()) {
            int key = entry.getKey();
            Map<Integer, Integer> neighborsCount = entry.getValue();
            System.out.println("Liczba " + key + " ma " + neighborsCount.size() + " sąsiadów:");
            for (Map.Entry<Integer, Integer> neighborEntry : neighborsCount.entrySet()) {
                int distanceSquared = neighborEntry.getKey();
                int count = neighborEntry.getValue();
                System.out.println("Odległość: " + Math.sqrt(distanceSquared) + ", Liczba sąsiadów: " + count);
            }
        }
    }
}
