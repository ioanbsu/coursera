package com.artigile.coursera.algorythms.week2;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author IoaN, 2/15/13 10:24 PM
 */
public class DijkstraTest {

    @Test
    public void testDijkstra() {
        Dijkstra dijkstra = new Dijkstra();
        Assert.assertEquals(8., dijkstra.calculateExpressionValue("(3+5)"));
        Assert.assertEquals(72., dijkstra.calculateExpressionValue("(3+5)*(3*3)"));
        Assert.assertEquals(48., dijkstra.calculateExpressionValue("(3+5)*(3+3)"));
        Assert.assertEquals(1., dijkstra.calculateExpressionValue("(3+5)/(10-2)"));
        Assert.assertEquals(144., dijkstra.calculateExpressionValue("(3+5)*(6+6+6)"));
    }
}
