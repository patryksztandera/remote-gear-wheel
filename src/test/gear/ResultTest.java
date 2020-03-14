package gear;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void shouldReturnVelocity(){
        Gear[] gear = new Gear[2];
        gear[0] = new Gear(20,30,10,15,150);
        gear[1] = new Gear(50,30,20,5);

        Result result = new Result();
        result.check(gear);

        assertEquals(75,gear[1].velocity,0);
        assertEquals("L",gear[1].direction);
    }

    @Test
    public void shouldReturnNoVelocity(){
        Gear[] gear = new Gear[2];
        gear[0] = new Gear(20,30,10,15,150);
        gear[1] = new Gear(150,30,20,5);

        Result result = new Result();
        result.check(gear);

        assertEquals("rolka w spoczynku",gear[1].direction);
    }

    @Test
    public void shouldReturnErrorOfVelocityConflict(){
        Gear[] gear = new Gear[2];
        gear[0] = new Gear(20,30,10,20,150);
        gear[1] = new Gear(50,30,20,10);

        Result result = new Result();
        result.check(gear);

        assertEquals("konflikt predkosci obrotowych rolek",result.getError2());
    }

    @Test
    public void shouldReturnErrorOfRadiusSize(){
        Gear[] gear = new Gear[2];
        gear[0] = new Gear(20,30,10,15,150);
        gear[1] = new Gear(50,30,22,5);

        Result result = new Result();
        result.check(gear);

        assertEquals("nakladajace sie tarcze",result.getError1());
    }
}
