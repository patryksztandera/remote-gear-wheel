package gear;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



public class Main {

    public static void main(String[] args) throws IOException
    {
        String fileName = "daneWyjscioweTest.json";
        PrintWriter clear = new PrintWriter(fileName);
        clear.close(); // czyszczenie pliku

        Parser parse = new Parser();

        parse.setInputPathFile("daneWejsciowe.json");

        parse.setRegexDrive("naped");
        parse.setRegexRoller("rolki");

        parse.setCoordinateX("x");
        parse.setCoordinateY("y");
        parse.setBottomRadius("rd");
        parse.setTopRadius("rg");
        parse.setVelocity("n");

        parse.json();

        List<Integer> systemSize = new ArrayList<>(parse.getListOfSizes()); //lista rozmiarow tablic kol zebatych

        for (int i = 0; i<systemSize.size();i++)
        {
            Result result = new Result();
            result.check(parse.gears(i));

            result.allWriteJson(fileName,systemSize.size(),i);
        }
    }
}
