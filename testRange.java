package org.jfree.data.test;
import org.jfree.data.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testRange {
    Range range = new Range(1,50);
    Range rangeNeg = new Range(-50,-1);

    // Range rangeWrong = new Range(100,50); -> produces an error of not having lower bound less than upper bound

    /* apply that to all the function that require making a new range or use an existing one,
       won't take lowe bound greater than upper bound.

       -> combine, contains, constrain, equals, expand, getLength, getCentralValue, get LowerBound, getUpperBound, shift, and toString.
       The intersects function accepts having lower bound greater than upper bound.
     */

    @Test
    public void testContains(){

        // integers
        Assertions.assertFalse(range.contains(-10));
        Assertions.assertFalse(range.contains(0));
        Assertions.assertTrue(range.contains(7));
        Assertions.assertTrue(range.contains(50));
        Assertions.assertFalse(range.contains(51));
        Assertions.assertFalse(range.contains(55));
        Assertions.assertFalse(range.contains(100));

        // floats
        Assertions.assertFalse(range.contains(0.5));
        Assertions.assertTrue(range.contains(1.1));
        Assertions.assertTrue(range.contains(7.9));
        Assertions.assertTrue(range.contains(49.9));
        Assertions.assertFalse(range.contains(50.8));
        Assertions.assertFalse(range.contains(55.4));
        Assertions.assertFalse(range.contains(100.5));

        // failed cases
        Assertions.assertTrue(range.contains(1));
    }

    @Test
    public void testContainsNeg(){
        Assertions.assertTrue(rangeNeg.contains(-40));
        Assertions.assertFalse(rangeNeg.contains(-100));
        Assertions.assertTrue(rangeNeg.contains(-45.9));
        Assertions.assertFalse(rangeNeg.contains(-100.4));

        Assertions.assertFalse(rangeNeg.contains(0));
        Assertions.assertTrue(rangeNeg.contains(-1));
        Assertions.assertFalse(rangeNeg.contains(5));
        Assertions.assertTrue(rangeNeg.contains(-5.7));
        Assertions.assertFalse(rangeNeg.contains(15.4));

        // failed cases
        Assertions.assertTrue(rangeNeg.contains(-50));
    }

    @Test
    public void testCombine(){
        Assertions.assertNull(Range.combine(null, null));
        //int
        Range rangeTest = new Range(2,9);
        Range rangeTest1 = new Range(2,5);
        Range rangeTest2 = new Range(3,9);
        Assertions.assertEquals(rangeTest1, Range.combine(rangeTest1, null));
        Assertions.assertEquals(rangeTest2, Range.combine(null, rangeTest2));

        //float
        Range rangeTestFloat = new Range(2.1,11.7);
        Range rangeTestFloat1 = new Range(2.1,5.2);
        Range rangeTestFloat2 = new Range(3.6,11.7);
        Assertions.assertEquals(rangeTestFloat1, Range.combine(rangeTestFloat1, null));
        Assertions.assertEquals(rangeTestFloat2, Range.combine(null, rangeTestFloat2));


        // failed cases
        Assertions.assertEquals(rangeTest, Range.combine(rangeTest1, rangeTest2));
        Assertions.assertEquals(rangeTestFloat, Range.combine(rangeTestFloat1, rangeTestFloat2));
    }

    @Test
    public void testCombineNeg(){
        Assertions.assertNull(Range.combine(null, null));
        //int
        Range rangeTest = new Range(-40,-10);
        Range rangeTest1 = new Range(-40,-30);
        Range rangeTest2 = new Range(-30,-10);
        Assertions.assertEquals(rangeTest1, Range.combine(rangeTest1, null));
        Assertions.assertEquals(rangeTest2, Range.combine(null, rangeTest2));

        //float
        Range rangeTestFloat = new Range(-11.7,-3.6);
        Range rangeTestFloat1 = new Range(-5.2,-3.6);
        Range rangeTestFloat2 = new Range(-11.7,-2.1);
        Assertions.assertEquals(rangeTestFloat1, Range.combine(rangeTestFloat1, null));
        Assertions.assertEquals(rangeTestFloat2, Range.combine(null, rangeTestFloat2));

        // failed cases
        Assertions.assertEquals(rangeTest, Range.combine(rangeTest1, rangeTest2));
        Assertions.assertEquals(rangeTestFloat, Range.combine(rangeTestFloat1, rangeTestFloat2));
    }

    @Test
    public void testConstrain(){
        // integers
        Assertions.assertEquals(1, range.constrain(0));
        Assertions.assertEquals(1, range.constrain(1));
        Assertions.assertEquals(7, range.constrain(7));
        Assertions.assertEquals(37, range.constrain(37));
        Assertions.assertEquals(50, range.constrain(50));
        Assertions.assertEquals(50, range.constrain(51));
        Assertions.assertEquals(50, range.constrain(55));
        Assertions.assertEquals(50, range.constrain(100));

        // floats
        Assertions.assertEquals(1, range.constrain(0.5));
        Assertions.assertEquals(1.1, range.constrain(1.1));
        Assertions.assertEquals(7.9, range.constrain(7.9));
        Assertions.assertEquals(37.9, range.constrain(37.9));
        Assertions.assertEquals(49.9, range.constrain(49.9));
        Assertions.assertEquals(50, range.constrain(50.8));
        Assertions.assertEquals(50, range.constrain(55.4));
        Assertions.assertEquals(50, range.constrain(100.5));
    }

    @Test
    public void testConstrainNeg(){
        // integers
        Assertions.assertEquals(-50, rangeNeg.constrain(-52));
        Assertions.assertEquals(-1, rangeNeg.constrain(-1));
        Assertions.assertEquals(-30, rangeNeg.constrain(-30));

        // floats
        Assertions.assertEquals(-1, rangeNeg.constrain(0.5));
        Assertions.assertEquals(-1.1, rangeNeg.constrain(-1.1));
        Assertions.assertEquals(-50, rangeNeg.constrain(-70.9));
    }

    @Test
    public void testEquals(){
        Range eqRan = new Range(3,50);
        Assertions.assertFalse(range.equals(54));
        Assertions.assertFalse(range.equals(5.4));
        Assertions.assertFalse(range.equals(eqRan));

        eqRan = new Range(1,40);
        Assertions.assertFalse(range.equals(eqRan));

        eqRan = new Range(1,50);
        Assertions.assertTrue(range.equals(eqRan));
    }

    @Test
    public void testEqualsNeg(){
        Range eqRan = new Range(-50,-3);
        Assertions.assertFalse(rangeNeg.equals(54));
        Assertions.assertFalse(rangeNeg.equals(5.4));
        Assertions.assertFalse(rangeNeg.equals("test"));
        Assertions.assertFalse(rangeNeg.equals(eqRan));

        eqRan = new Range(-40,-1);
        Assertions.assertFalse(rangeNeg.equals(eqRan));

        eqRan = new Range(-50,-1);
        Assertions.assertTrue(rangeNeg.equals(eqRan));
    }

    @Test
    public void testExpand(){
        Assertions.assertNotEquals(new Range(2,6), Range.expand(new Range(2,6),0.25,0.5));
        Assertions.assertNotEquals(new Range(0.25,0.5), Range.expand(new Range(0,0),0.25,0.5));

        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> Range.expand(null,0.25,0.5));
        Assertions.assertEquals("Null 'range' argument.", exception.getMessage());

        // failed cases
        Assertions.assertEquals(new Range(1,8), Range.expand(new Range(2,6),0.25,0.5));
    }

    @Test
    public void testExpandNeg(){
        Assertions.assertNotEquals(new Range(-6,-2), Range.expand(new Range(-6,-2),0.25,0.5));
        Assertions.assertEquals(new Range(0,0), Range.expand(new Range(0,0),0.25,0.5));

        // failed cases
        Assertions.assertEquals(new Range(-7,0), Range.expand(new Range(-6,-2),0.25,0.5));
    }

    @Test
    public void testExpandToInclude(){
        Assertions.assertEquals(new Range(1,8), Range.expandToInclude(new Range(1,6),8));
        Assertions.assertEquals(new Range(1,8), Range.expandToInclude(new Range(3,8),1));
        Assertions.assertEquals(new Range(8,8), Range.expandToInclude(null,8));
        Assertions.assertEquals(new Range(1,8), Range.expandToInclude(new Range(1,8),7));
    }

    @Test
    public void testExpandToIncludeNeg(){
        Assertions.assertEquals(new Range(-8,-1), Range.expandToInclude(new Range(-6,-1),-8));
        Assertions.assertEquals(new Range(-8,-1), Range.expandToInclude(new Range(-8,-3),-1));
        Assertions.assertEquals(new Range(-8,-8), Range.expandToInclude(null,-8));
        Assertions.assertEquals(new Range(-8,-1), Range.expandToInclude(new Range(-8,-1),-7));
    }

    @Test
    public void testGetCentralValue(){
        Assertions.assertEquals(25.5, range.getCentralValue());
        Assertions.assertEquals(11.25, new Range(1.5,21).getCentralValue());
    }

    @Test
    public void testGetCentralValueNeg(){
        Assertions.assertEquals(-25.5, rangeNeg.getCentralValue());
        Assertions.assertEquals(-11.25, new Range(-21,-1.5).getCentralValue());
        Assertions.assertEquals(-11.35, new Range(-21.2,-1.5).getCentralValue());
    }

    @Test
    public void testGetLength(){
        Assertions.assertEquals(49, range.getLength());
        Assertions.assertEquals(17.5, new Range(2.5,20).getLength());
        Assertions.assertEquals(17.75, new Range(2.5,20.25).getLength());
        Assertions.assertEquals(17.7, new Range(2.3,20).getLength());
        Assertions.assertNotEquals(50, range.getLength());
        Assertions.assertNotEquals(2.5, new Range(2.5,20).getLength());

        Assertions.assertEquals(9.9, new Range(10.7,20.6).getLength(),0.5);
        //failed
        Assertions.assertEquals(9.9, new Range(10.7,20.6).getLength());
    }

    @Test
    public void testGetLengthNeg(){
        Assertions.assertEquals(49, rangeNeg.getLength());
        Assertions.assertEquals(17.5, new Range(-20,-2.5).getLength());
        Assertions.assertNotEquals(50, rangeNeg.getLength());
        Assertions.assertNotEquals(-20, new Range(-20,-2.5).getLength());
    }

    @Test
    public void testGetLowerBound(){
        Range tesRan = new Range(50,100);
        Assertions.assertEquals(1,range.getLowerBound());
        Assertions.assertEquals(50,tesRan.getLowerBound());
        Assertions.assertNotEquals(50,range.getLowerBound());
        Assertions.assertNotEquals(100,tesRan.getLowerBound());
    }

    @Test
    public void testGetLowerBoundNeg(){
        Range tesRan = new Range(-100,-50);
        Assertions.assertEquals(-50,rangeNeg.getLowerBound());
        Assertions.assertEquals(-100,tesRan.getLowerBound());
        Assertions.assertNotEquals(-1,range.getLowerBound());
        Assertions.assertNotEquals(-50,tesRan.getLowerBound());
    }

    @Test
    public void testGetUpperBound(){
        Range tesRan = new Range(50,100);
        Assertions.assertEquals(50,range.getUpperBound());
        Assertions.assertEquals(100,tesRan.getUpperBound());
        Assertions.assertNotEquals(1,range.getUpperBound());
        Assertions.assertNotEquals(50,tesRan.getUpperBound());
    }

    @Test
    public void testGetUpperBoundNeg(){
        Range tesRan = new Range(-100,-50);
        Assertions.assertEquals(-1,rangeNeg.getUpperBound());
        Assertions.assertEquals(-50,tesRan.getUpperBound());
        Assertions.assertNotEquals(-50,range.getUpperBound());
        Assertions.assertNotEquals(-100,tesRan.getUpperBound());
    }

    @Test
    public void testIntersects(){
        Range tesRan = new Range(50,100);
        Assertions.assertTrue(range.intersects(2,50));
        Assertions.assertTrue(range.intersects(50,51));
        Assertions.assertTrue(range.intersects(5.5,25.8));

        Assertions.assertTrue(tesRan.intersects(60,70));
        Assertions.assertTrue(tesRan.intersects(80.5,90.4));
        Assertions.assertFalse(tesRan.intersects(120,25));

        //failed cases
        Assertions.assertFalse(range.intersects(0,0));
        Assertions.assertFalse(range.intersects(100,150));
        Assertions.assertFalse(tesRan.intersects(1,25));
        Assertions.assertFalse(tesRan.intersects(125,150));

    }

    @Test
    public void testIntersectsNeg(){
        Range tesRan = new Range(-100,-50);
        Assertions.assertTrue(rangeNeg.intersects(-50,-2));
        Assertions.assertTrue(rangeNeg.intersects(-51,-50));
        Assertions.assertTrue(rangeNeg.intersects(-25.8,-5.5));

        Assertions.assertTrue(tesRan.intersects(-70,-60));
        Assertions.assertTrue(tesRan.intersects(-90.4,-80.5));
        Assertions.assertFalse(tesRan.intersects(-25,-120));

        //failed cases
        Assertions.assertFalse(rangeNeg.intersects(0,0));
        Assertions.assertFalse(rangeNeg.intersects(-150,-100));
        Assertions.assertFalse(tesRan.intersects(-25,-1));
        Assertions.assertFalse(tesRan.intersects(-150,-125));
    }

    @Test
    public void testShift(){
        Range shifted = new Range(3,8);
        Assertions.assertEquals(new Range(5,10), Range.shift(shifted,2));
        Assertions.assertNotEquals(new Range(1,6), Range.shift(shifted,2));

        Assertions.assertEquals(new Range(1,6), Range.shift(shifted,-2));
        Assertions.assertNotEquals(new Range(3,8), Range.shift(shifted,-2));

        Assertions.assertNotEquals(new Range(-4,1), Range.shift(shifted,-7));

        shifted = new Range(-8,-1);
        Assertions.assertNotEquals(new Range(-1,6), Range.shift(shifted,7));

        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> Range.shift(null,-2));
        Assertions.assertEquals("Null 'base' argument.", exception.getMessage());

    }

    @Test
    public void testShiftNeg(){
        Range shifted = new Range(-8,-3);
        Assertions.assertEquals(new Range(-6,-1), Range.shift(shifted,2));
        Assertions.assertNotEquals(new Range(-10,-5), Range.shift(shifted,2));

        Assertions.assertEquals(new Range(-10,-5), Range.shift(shifted,-2));
        Assertions.assertNotEquals(new Range(-6,-1), Range.shift(shifted,-2));

        Assertions.assertEquals(new Range(-15,-10), Range.shift(shifted,-7));

        shifted = new Range(-8,-1);
        Assertions.assertNotEquals(new Range(-1,6), Range.shift(shifted,7));
    }

    @Test
    public void testShiftBool(){
        Range shifted = new Range(3,8);
        Assertions.assertEquals(new Range(5,10), Range.shift(shifted,2,true));
        Assertions.assertEquals(new Range(5,10), Range.shift(shifted,2,false));
        Assertions.assertNotEquals(new Range(1,6), Range.shift(shifted,2,true));
        Assertions.assertNotEquals(new Range(1,6), Range.shift(shifted,2,false));

        Assertions.assertEquals(new Range(1,6), Range.shift(shifted,-2,false));
        Assertions.assertNotEquals(new Range(3,8), Range.shift(shifted,-2,false));
        Assertions.assertEquals(new Range(1,6), Range.shift(shifted,-2,true));
        Assertions.assertNotEquals(new Range(3,8), Range.shift(shifted,-2,true));

        Assertions.assertNotEquals(new Range(-4,1), Range.shift(shifted,-7, false));
        Assertions.assertEquals(new Range(-4,1), Range.shift(shifted,-7, true));

        shifted = new Range(-8,-1);
        Assertions.assertNotEquals(new Range(-1,6), Range.shift(shifted,7,false)); //to not do the zeroCrossing (from negative to positive)
        Assertions.assertEquals(new Range(-1,6), Range.shift(shifted,7,true));

        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> Range.shift(null,-2, true));
        Assertions.assertEquals("Null 'base' argument.", exception.getMessage());
    }

    @Test
    public void testShiftBoolNeg(){
        Range shifted = new Range(-8,-3);
        Assertions.assertEquals(new Range(-6,-1), Range.shift(shifted,2,true));
        Assertions.assertEquals(new Range(-6,-1), Range.shift(shifted,2,false));
        Assertions.assertNotEquals(new Range(-10,-5), Range.shift(shifted,2,true));
        Assertions.assertNotEquals(new Range(-10,-5), Range.shift(shifted,2,false));

        Assertions.assertEquals(new Range(-10,-5), Range.shift(shifted,-2,false));
        Assertions.assertNotEquals(new Range(-6,-1), Range.shift(shifted,-2,false));
        Assertions.assertEquals(new Range(-10,-5), Range.shift(shifted,-2,true));
        Assertions.assertNotEquals(new Range(-6,-1), Range.shift(shifted,-2,true));

        Assertions.assertEquals(new Range(-15,-10), Range.shift(shifted,-7, true));

        shifted = new Range(-8,-1);
        Assertions.assertNotEquals(new Range(-1,6), Range.shift(shifted,7,false)); //to not do the zeroCrossing (from negative to positive)
        Assertions.assertEquals(new Range(-1,6), Range.shift(shifted,7,true));
    }

    @Test
    public void testToString(){
        String str= "Range["+range.getLowerBound()+","+range.getUpperBound()+"]";
        Assertions.assertEquals(str, range.toString());
        str= "Range["+range.getUpperBound()+","+range.getLowerBound()+"]";
        Assertions.assertNotEquals(str, range.toString());
    }

    @Test
    public void testToStringNeg(){
        String str= "Range["+rangeNeg.getLowerBound()+","+rangeNeg.getUpperBound()+"]";
        Assertions.assertEquals(str, rangeNeg.toString());
        str= "Range["+rangeNeg.getUpperBound()+","+rangeNeg.getLowerBound()+"]";
        Assertions.assertNotEquals(str, rangeNeg.toString());
    }
}