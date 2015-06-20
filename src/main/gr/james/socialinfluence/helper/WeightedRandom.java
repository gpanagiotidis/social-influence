package gr.james.socialinfluence.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class WeightedRandom {
    // Efraimidis, Spirakis. "Weighted random sampling with a reservoir.".
    public static <E> List<E> makeRandomSelection(Map<E, Double> weightMap, int selections) {
        ArrayList<E> finalSelections = new ArrayList<E>();
        PriorityQueue<ObjectWithWeight<E>> keyQueue = new PriorityQueue<ObjectWithWeight<E>>();
        for (Map.Entry<E, Double> e : weightMap.entrySet()) {
            keyQueue.add(new ObjectWithWeight<E>(e.getKey(), Math.pow(RandomHelper.getRandom().nextDouble(), 1.0 / e.getValue())));
        }
        while (finalSelections.size() < selections) {
            finalSelections.add(keyQueue.poll().e);
        }
        return finalSelections;
    }

    private static class ObjectWithWeight<E> implements Comparable {
        public E e;
        public double w;

        public ObjectWithWeight(E e, double w) {
            this.e = e;
            this.w = w;
        }

        @Override
        public int compareTo(Object o) {
            ObjectWithWeight other = (ObjectWithWeight) o;
            return Double.compare(other.w, this.w);
        }
    }
}