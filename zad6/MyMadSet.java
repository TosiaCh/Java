import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class MyMadSet implements MadSet {

    private DistanceMeasure distanceMeasure;
    private double min;
    private List<Point> listaPunktow;
    private List<Point> removedPoints;

    public MyMadSet() {
        this.listaPunktow = new ArrayList<>();
        this.removedPoints = new ArrayList<>();

    }

    public void setDistanceMeasure(DistanceMeasure measure) throws TooCloseException {
        if (!removedPoints.isEmpty()) {
            throw new TooCloseException(removedPoints);
        }
        this.distanceMeasure = measure;
        
        if (!listaPunktow.isEmpty()) {
            List<Point> tempRemovedPoints = new ArrayList<>();
            for (int i = 0; i < listaPunktow.size(); i++) {
                for (int j = i + 1; j < listaPunktow.size(); j++) {
                    double dis = distanceMeasure.distance(listaPunktow.get(i), listaPunktow.get(j));
                    if (dis < min) {
                        Point point1 = listaPunktow.get(i);
                        Point point2 = listaPunktow.get(j);

                        if (!tempRemovedPoints.contains(point1)) {
                            tempRemovedPoints.add(point1);
                        }

                        if (!tempRemovedPoints.contains(point2)) {
                            tempRemovedPoints.add(point2);
                        }
                    }

                }
            }
            removedPoints.addAll(tempRemovedPoints);

            if (!removedPoints.isEmpty()) {
                throw new TooCloseException(removedPoints);
            }
        }

    }

    public void setMinDistanceAllowed(double minAllowed) throws TooCloseException {
        if (!removedPoints.isEmpty()) {
            throw new TooCloseException(removedPoints);
        }
        this.min = minAllowed;
        if (!listaPunktow.isEmpty()) {
            List<Point> tempRemovedPoints = new ArrayList<>();
            for (int i = 0; i < listaPunktow.size(); i++) {
                for (int j = i + 1; j < listaPunktow.size(); j++) {
                    double dis = distanceMeasure.distance(listaPunktow.get(i), listaPunktow.get(j));
                    if (dis < min) {
                        Point point1 = listaPunktow.get(i);
                        Point point2 = listaPunktow.get(j);

                        if (!tempRemovedPoints.contains(point1)) {
                            tempRemovedPoints.add(point1);
                        }

                        if (!tempRemovedPoints.contains(point2)) {
                            tempRemovedPoints.add(point2);
                        }
                    }

                }
            }
            removedPoints.addAll(tempRemovedPoints);

            if (!removedPoints.isEmpty()) {
                throw new TooCloseException(removedPoints);
            }
        }

    }

    public void addPoint(Point point) throws TooCloseException {
        int i = 0;
        for (Point istniejacyPunkt : listaPunktow) {
            double dis = distanceMeasure.distance(istniejacyPunkt, point);
            if (dis < min) {
                removedPoints.add(istniejacyPunkt);
                i++;
            }
        }
        if (i != 0) {
            removedPoints.add(point);

        } else {
            listaPunktow.add(point);
        }

        if (!removedPoints.isEmpty()) {
            throw new TooCloseException(removedPoints);
        }
        removedPoints.clear();
    }

    public List<Point> getPoints() {
        return Collections.unmodifiableList(listaPunktow);
    }

    public List<Point> getSortedPoints(Point referencePoint) {
        Comparator<Point> porownaniePunktow = Comparator
                .comparingDouble(pkt -> distanceMeasure.distance(referencePoint, pkt));
        listaPunktow.sort(porownaniePunktow);
        return new ArrayList<>(listaPunktow);
    }
}
