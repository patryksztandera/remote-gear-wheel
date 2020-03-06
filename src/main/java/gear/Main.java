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

        List<Integer> allNumberData = new ArrayList<>(parse.getListOfAllValues()); // lista liczb prawdziwa
        List<Integer> systemSize = new ArrayList<>(parse.getListOfSizes()); //lista rozmiarow tablic kol zebatych

        for (int i = 0; i<systemSize.size();i++)
        {
            Gear[] gears = new Gear[systemSize.get(i)];
            for (int j = 0; j<systemSize.get(i); j++)
            {
                int indexDrive = 0; // index pierwszego parametru napedu
                int indexGear; //index pierwszego parametru kola nienapedzanego
                if (i == 0)
                {
                    indexGear = j * 4 + 1; //bo w dalszym nie dodaje rozmiaru zerowej tablicy
                }
                else
                {
                    for (int z = 0; z < i; z++)
                    {
                        indexDrive = indexDrive + systemSize.get(z); //suma elementow iteracyjnie
                    }
                    indexDrive = indexDrive * 4 + i;
                    indexGear = j * 4 + 1 + indexDrive ;
                }

                if (j == 0 )
                {
                    gears[0] = new Gear(allNumberData.get(indexDrive),
                            allNumberData.get(indexDrive+1),
                            allNumberData.get(indexDrive+2),
                            allNumberData.get(indexDrive+3),
                            allNumberData.get(indexDrive+4));
                }
                else
                {
                    gears[j] = new Gear(allNumberData.get(indexGear),
                            allNumberData.get(indexGear+1),
                            allNumberData.get(indexGear+2),
                            allNumberData.get(indexGear+3));
                }
            }

            Result result = new Result();
            result.check(gears);

            result.allWriteJson(fileName,systemSize.size(),i);
        }
    }
}
