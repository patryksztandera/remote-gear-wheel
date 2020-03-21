package gear;

import java.io.IOException;
import javax.json.*;
import java.util.Collections;
import java.io.*;

class Parser {

    private String inputPathFile;

    private String arrayOfGears;
    private String regexDrive;
    private String regexRoller;

    private String coordinateX;
    private String coordinateY;
    private String bottomRadius;
    private String topRadius;
    private String velocity;

    Parser(){}

    Gear[] gears(int gearFromArray) throws IOException{

        int arraySize;

        JsonReaderFactory readerFactory = Json.createReaderFactory(Collections.emptyMap());
        try (JsonReader jsonReader = readerFactory.createReader(new FileInputStream(inputPathFile))) {

            JsonObject jsonObject = jsonReader.readObject();
            arraySize = jsonObject.getJsonArray(arrayOfGears)
                    .get(gearFromArray).asJsonObject()
                    .getJsonArray(regexRoller)
                    .size();
        }

        arraySize++;

        //create gear table with first drive roll and other rolls which should be driven by the first one
        Gear[] gear = new Gear[arraySize];
        gear[0] = drive(gearFromArray);

        for (int i = 1; i < arraySize; i++){
            int k = i - 1;
            gear[i] = arrayWithoutDrive(gearFromArray,k);
        }

        return gear;
    }

    private Gear arrayWithoutDrive(int gearFromArray, int i)throws IOException{

        Gear gear;

        JsonReaderFactory readerArray = Json.createReaderFactory(Collections.emptyMap());
        try (JsonReader jsonReader = readerArray.createReader(new FileInputStream(inputPathFile))) {

            JsonObject jsonObject = jsonReader.readObject();

            gear = new Gear(
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonArray(regexRoller)
                            .get(i).asJsonObject()
                            .getInt(coordinateX),
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonArray(regexRoller)
                            .get(i).asJsonObject()
                            .getInt(coordinateY),
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonArray(regexRoller)
                            .get(i).asJsonObject()
                            .getInt(bottomRadius),
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonArray(regexRoller)
                            .get(i).asJsonObject()
                            .getInt(topRadius)
            );
        }
        return gear;
    }

    private Gear drive(int gearFromArray)throws IOException{

        Gear gear ;

        JsonReaderFactory readerArray = Json.createReaderFactory(Collections.emptyMap());
        try (JsonReader jsonReader = readerArray.createReader(new FileInputStream(inputPathFile))) {

            JsonObject jsonObject = jsonReader.readObject();

            gear = new Gear(
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonObject(regexDrive)
                            .getInt(coordinateX),
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonObject(regexDrive)
                            .getInt(coordinateY),
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonObject(regexDrive)
                            .getInt(bottomRadius),
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonObject(regexDrive)
                            .getInt(topRadius),
                    jsonObject.getJsonArray(arrayOfGears)
                            .get(gearFromArray).asJsonObject()
                            .getJsonObject(regexDrive)
                            .getInt(velocity)
            );
        }
        return gear;
    }

    void setInputPathFile(String inputPathFile){ this.inputPathFile = inputPathFile; }

    void setArrayOfGears(String arrayOfGears){ this.arrayOfGears = arrayOfGears; }

    void setRegexDrive(String regexDrive){ this.regexDrive = regexDrive; }

    void setRegexRoller(String regexRoller){ this.regexRoller = regexRoller; }

    void setCoordinateX(String coordinateX){ this.coordinateX = coordinateX; }

    void setCoordinateY(String coordinateY){ this.coordinateY = coordinateY; }

    void setBottomRadius(String bottomRadius){ this.bottomRadius = bottomRadius; }

    void setTopRadius(String topRadius){ this.topRadius = topRadius; }

    void setVelocity(String velocity){ this.velocity = velocity; }

}
