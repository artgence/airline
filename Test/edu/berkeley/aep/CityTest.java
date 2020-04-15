package edu.berkeley.aep;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CityTest {

    City a = new City();
    City b = new City();
    City c = new City();
    City d = new City();
    City e = new City();
    City f = new City();
    City g = new City();
    City h = new City();

    public CityTest(){
        h.addChild(b);
        b.addChild(a);
        a.addChild(f);
        b.addChild(c);
        c.addChild(d);
        c.addChild(e);
        c.addChild(e);
        d.addChild(e);
        e.addChild(b);

        h.addPathCost(b, 600);
        b.addPathCost(a, 200);
        a.addPathCost(f, 100);
        b.addPathCost(c, 50);
        c.addPathCost(d, 50);
        c.addPathCost(e, 200);
        c.addPathCost(e, 400);
        d.addPathCost(e, 100);
        e.addPathCost(b, 500);
    }

    //DFS test
    @Test
    public void nodeShouldBeAbleToReachItself(){
        City city = new City();
        assertTrue(city.canReach(city));
    }

    @Test
    public void hShouldBeAbleToReachB(){
        assertTrue(h.canReach(b));
    }

    @Test
    public void hShouldNotBeAbleToReachG() {
        assertFalse(h.canReach(g));
    }

    //DFS test
    @Test
    public void nodeShouldBe0HopToReachItself() {
        City city = new City();
        assertEquals(0, city.getHops(city));
    }

    @Test
    public void hShouldBeMAX_VALUEHopToReachG() {
        assertEquals(Integer.MAX_VALUE,h.getHops(g));
    }

    @Test
    public void hShouldBe1HopToReachB() {
        assertEquals(1,h.getHops(b));
    }

    @Test
    public void hShouldBe4HopsToReachE(){
        assertEquals(4,h.getHops(e));
    }

    @Test
    public void hShouldBe3HopsToReachF() { assertEquals(3, h.getHops(f)); }

    //BFS test
    @Test
    public void hShouldBe3HopsToMinReachE(){
        assertEquals(3,h.getMinHops(e));
    }

    @Test
    public void hShouldBe1HopToMinReachB() {
        assertEquals(1,h.getMinHops(b));
    }

    @Test
    public void hShouldBeMAX_VALUEHopToMinReachG() {
        assertEquals(Integer.MAX_VALUE,h.getMinHops(g));
    }

    @Test
    public void cShouldBe2HopsToMinReachB() {
        assertEquals(2, c.getMinHops(b));
    }

    //Uniform Cost Search Test
    @Test
    public void hShouldBeMAX_VALUECostToReachG() {
        assertEquals(Integer.MAX_VALUE, h.uniformCostSearch(g));
    }

    @Test
    public void aShouldBe0CostToReachA() {
        assertEquals(0, a.uniformCostSearch(a));
    }

    @Test
    public void cShouldBe150$ToReachE() {
        assertEquals(150, c.uniformCostSearch(e));
    }

    @Test
    public void bShouldBe200$ToReacha() {
        assertEquals(200, b.uniformCostSearch(a));
    }

    @Test
    public void cShouldBe650$ToReachB() {
        assertEquals(650, c.uniformCostSearch(b));
    }

}
