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

        String inputFilePath = "inputData.json";
        String outputFilePath = "outputData.json";
        PrintWriter clearFile = new PrintWriter(outputFilePath);
        clearFile.close();

        String arrayOfGears = "gears";

        Parser parse = new Parser();

        parse.setInputPathFile(inputFilePath);

        parse.setArrayOfGears(arrayOfGears);
        parse.setRegexDrive("drive");
        parse.setRegexRoller("rollers");

        parse.setCoordinateX("x");
        parse.setCoordinateY("y");
        parse.setBottomRadius("rb");
        parse.setTopRadius("rt");
        parse.setVelocity("v");

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
