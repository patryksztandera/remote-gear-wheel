package gear;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;




public class Main {

    public static void main(String[] args) throws IOException {

        String inputFilePath = "daneWejsciowe.json";
        String outputFilePath = "daneWyjsciowe.json";
        PrintWriter clearFile = new PrintWriter(outputFilePath);
        clearFile.close();

        String arrayOfGears = "uklady";

        Parser parse = new Parser();

        parse.setInputPathFile(inputFilePath);

        parse.setArrayOfGears(arrayOfGears);
        parse.setRegexDrive("naped");
        parse.setRegexRoller("rolki");

        parse.setCoordinateX("x");
        parse.setCoordinateY("y");
        parse.setBottomRadius("rd");
        parse.setTopRadius("rg");
        parse.setVelocity("n");

        int arraySize;

        JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
        try (JsonReader jsonReader = readerFactory.createReader(new FileInputStream(inputFilePath))) {

            JsonObject jsonObject = jsonReader.readObject();
            arraySize = jsonObject.getJsonArray(arrayOfGears).size();
        }

        for (int i = 0; i<arraySize; i++) {

            Result result = new Result();
            result.check(parse.gears(i));
            result.allWriteJson(outputFilePath,arraySize,i);
        }
    }
}
