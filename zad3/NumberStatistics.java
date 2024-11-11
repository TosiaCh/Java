import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumberStatistics implements Statistics {

    private int length;
    private Map<Integer, Set<Position>> numberPositions = new HashMap();

    @Override
    public void sideLength(int length) {
        this.length = length;
    }

    // klucz- cyfry, wartość zbiór połozenia(col,row)
    @Override
    public void addNumbers(Map<Integer, Set<Position>> numberPositions) {
        this.numberPositions = numberPositions;
    }

    // cyfra,dla jakiej odległości,ile cyfr
    @Override
    public Map<Integer, Map<Integer, Integer>> neighbours(Position position, int maxDistanceSquared) {

        Map<Integer, Map<Integer, Integer>> wyniki = new HashMap<>();

        for (Map.Entry<Integer, Set<Position>> entry : numberPositions.entrySet()) {
            int klucz = entry.getKey();
            Set<Position> zbior = entry.getValue();

            Map<Integer, Integer> neighborsCount = new HashMap();

            for (Position punkty : zbior) {

                int colDis = position.col() - punkty.col();
                int rowDis = position.row() - punkty.row();
                colDis = Math.abs(colDis);
                rowDis = Math.abs(rowDis);
                colDis = Math.min(colDis, length - colDis);
                rowDis = Math.min(rowDis, length - rowDis);
                int distanceSquared = (colDis * colDis) + (rowDis * rowDis);
                if (distanceSquared <= maxDistanceSquared) {
                    neighborsCount.put(distanceSquared, neighborsCount.getOrDefault(distanceSquared, 0) + 1);
                }
            }
            if (!neighborsCount.isEmpty()) {
                wyniki.put(klucz, neighborsCount);
            }
        }

        return wyniki;
    }
}
