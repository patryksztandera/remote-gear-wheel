package gear;

import org.junit.Test;
import static org.junit.Assert.*;

public class GearTest {

    @Test
    public void shouldReturnRightDirection(){
        Gear gear = new Gear(1,1,1,1,100);
        assertEquals("R",gear.direction);
    }

    @Test
    public void shouldReturnLeftDirection(){
        Gear gear = new Gear(1,1,1,1,-100);
        assertEquals("L",gear.direction);
    }

    @Test
    public void shouldReturnNoDirection(){
        Gear gear = new Gear(1,1,1,1,0);
        assertEquals("rolka w spoczynku",gear.direction);
    }
}
