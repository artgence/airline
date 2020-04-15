package edu.berkeley.aep;

import java.util.*;

public class City {
    private List<City> children = new ArrayList<>();
    private HashMap<City, Integer> pathCost = new HashMap<>();
    public final static int UNREACHABLE = Integer.MAX_VALUE;

    public boolean canReach(City arrivalCity) {
        return getHops(arrivalCity) != UNREACHABLE;
    }

    public void addChild(City city){
        children.add(city);
    }

    public List<City> getChildren() {
        return children;
    }

    public HashMap<City, Integer> getPathCost() {
        return pathCost;
    }

    public void addPathCost(City city, int cost) {
        pathCost.put(city, cost);
    }

    public int getHops(City arrivalCity) {
        return getHops(arrivalCity, new HashSet<>(), 0);
    }

    public int getHops(City arrivalCity, Set<City> visited, int hops) {
        if (this == arrivalCity) return hops;
        else if (!visited.contains(this)) {
            int nextHop = UNREACHABLE;
            visited.add(this);
            for (City childCity : children) {
                nextHop= childCity.getHops(arrivalCity, visited, hops + 1);
                if (nextHop != UNREACHABLE)
                    break;
            }
            return nextHop;
        }else return UNREACHABLE;
    }

    public int getMinHops(City arrivalCity){
        return getMinHops(arrivalCity, new HashSet<>(), new LinkedList<>(), 0);
    }

    public int getMinHops(City arrivalCity, Set<City> visited, LinkedList<City> nextVist, int hops) {
        if (this == arrivalCity) return hops;
        else if (!visited.contains(this)) {
            int nextHop = UNREACHABLE;
            visited.add(this);
            for (City childCity: children)nextVist.push(childCity);
            if (nextVist.size() != 0) nextHop = nextVist.pop().getMinHops(arrivalCity, visited, nextVist, hops + 1);
            return nextHop;
        }else return UNREACHABLE;
    }

    public int uniformCostSearch(City arrivalCity) {
        PriorityQueue<CityKeyValue> frontier = new PriorityQueue<>(new CityCostComparator());
        CityKeyValue cityKeyValue = new CityKeyValue(this, 0);
        frontier.offer(cityKeyValue);
        return uniformCostSearch(arrivalCity, frontier, new HashSet<>());
    }

    public int uniformCostSearch(City arrivalCity, PriorityQueue<CityKeyValue> frontier, Set<City> explored) {
        while (!frontier.isEmpty()) {
            CityKeyValue headCity = frontier.poll();
            if (headCity.getCity().equals(arrivalCity))
                return headCity.getMinCost();
            else {
                //add children nodes
                if (!explored.contains(headCity.getCity())) {
                    explored.add(headCity.getCity());
                    List<City> headCityChildren = headCity.getCity().getChildren();
                    for (City city: headCityChildren) {
                        CityKeyValue cityKeyValue1 = new CityKeyValue(city, headCity.getMinCost() + headCity.getCity().getPathCost().get(city));
                        List<CityKeyValue> toAdd = new ArrayList<CityKeyValue>();
                        Iterator iterator = frontier.iterator();
                        while (iterator.hasNext()) {
                            //update the min value
                            CityKeyValue cityKeyValue2 = (CityKeyValue) iterator.next();
                            if (cityKeyValue2.getCity().equals(city) && cityKeyValue2.getMinCost() > cityKeyValue1.getMinCost()) {
                                iterator.remove();
                                break;
                            }
                        }
                        toAdd.add(cityKeyValue1);
                        frontier.addAll(toAdd);
                    }
                }
            }
        }
        return UNREACHABLE;
    }

    class CityCostComparator implements Comparator<CityKeyValue>{
        @Override
        public int compare(CityKeyValue o1, CityKeyValue o2) {
            return o1.getMinCost() - o2.getMinCost();
        }
    }

}

class CityKeyValue {
    City city;
    int minCost;

    public City getCity() {
        return city;
    }

    public int getMinCost() {
        return minCost;
    }

    public CityKeyValue(City city, int minCost) {
        this.city = city;
        this.minCost = minCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityKeyValue that = (CityKeyValue) o;
        return Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city);
    }
}
