package gear;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parser {

    private List<Integer> valuesList;
    private List<Integer> gearsSizes;

    private String inputPathFile;

    private String regexDrive;
    private String regexRoller;

    private String coordinateX;
    private String coordinateY;
    private String bottomRadius;
    private String topRadius;
    private String velocity;

    Parser(){}

    void json() throws IOException{

        Path inputPath = Paths.get(inputPathFile);
        List<String> lines = Files.readAllLines(inputPath);
        valuesList = new ArrayList<>();
        gearsSizes = new ArrayList<>();

        Pattern patternDrive = Pattern.compile(regexDrive);
        Pattern patternRoller = Pattern.compile(regexRoller);

        String regex = ".*"+coordinateX+".*:\\s*(\\d+?)\\s*,.*|" +
                ".*"+coordinateY+".*:\\s*(\\d+?)\\s*,.*|" +
                ".*"+bottomRadius+".*:\\s*(\\d+?)\\s*,.*|" +
                ".*"+topRadius+".*:\\s*(\\d+).*|";

        Pattern patternDriveGroup = Pattern.compile(
                ".*"+coordinateX+".*:\\s*(\\d+?)\\s*,.*|" +
                ".*"+coordinateY+".*:\\s*(\\d+?)\\s*,.*|" +
                ".*"+bottomRadius+".*:\\s*(\\d+?)\\s*,.*|" +
                ".*"+topRadius+".*:\\s*(\\d+?)\\s*,.*|" +
                ".*"+velocity+".*:\\s*(\\d+).*");
        Pattern patternDriveLine = Pattern.compile(
                ".*"+coordinateX+".*:\\s*(\\d+?)\\s*," +
                ".*"+coordinateY+".*:\\s*(\\d+?)\\s*," +
                ".*"+bottomRadius+".*:\\s*(\\d+?)\\s*," +
                ".*"+topRadius+".*:\\s*(\\d+?)\\s*," +
                ".*"+velocity+".*:\\s*(\\d+).*");

        Pattern patternRollerGroup = Pattern.compile(regex);

        Pattern patternRollerLine = Pattern.compile(
                ".*"+coordinateX+".*:\\s*(\\d+?)\\s*," +
                ".*"+coordinateY+".*:\\s*(\\d+?)\\s*," +
                ".*"+bottomRadius+".*:\\s*(\\d+?)\\s*," +
                ".*"+topRadius+".*:\\s*(\\d+).*");

        Pattern patternStart = Pattern.compile(".*"+coordinateX+".*");

        Pattern patternEndObject = Pattern.compile(".*}.*");
        Matcher matcherEndObject;

        for (int i = 0; i < lines.size(); i++) {


            Matcher matcherDrive = patternDrive.matcher(lines.get(i));
            matcherEndObject = patternEndObject.matcher(lines.get(i));

            if (matcherDrive.find() && !matcherEndObject.find() )
            {
                int z = i;

                while (!Pattern.matches(".*}.*",lines.get(z)))
                {
                    Matcher matcherStart = patternStart.matcher(lines.get(z));
                    if (matcherStart.find())
                    {
                        searchGroup(lines,valuesList,patternDriveGroup,z,5);
                    }
                    z++;
                }
            }

            Pattern patternOneLineDrive = Pattern.compile(".*"+regexDrive+".*}.*");
            Matcher matcherOneLineDrive = patternOneLineDrive.matcher(lines.get(i));

            if ( matcherOneLineDrive.find())
            {
                searchLine(lines,valuesList,patternDriveLine,i,5);
            }


            Matcher matcherRoller = patternRoller.matcher(lines.get(i));

            if (matcherRoller.find())
            {
                int counter = 1; // od jedynki bo naped
                int z = i;

                while (!Pattern.matches(".*].*",lines.get(z)))
                {
                    Pattern patternStartObject = Pattern.compile(".*[{].*");
                    Matcher matcherStartObject = patternStartObject.matcher(lines.get(z));

                    matcherEndObject = patternEndObject.matcher(lines.get(z));

                    if (matcherStartObject.find() && !matcherEndObject.find())
                    {
                        int w = z;
                        while (!Pattern.matches(".*}.*",lines.get(w)))
                        {
                            Matcher matcherStart = patternStart.matcher(lines.get(w));
                            if (matcherStart.find())
                            {
                                searchGroup(lines,valuesList,patternRollerGroup,w,4);
                            }
                            w++;
                        }
                    }

                    findOneLineRoller(lines,valuesList,patternRollerLine,z);
                   
                    Matcher matcherStart = patternStart.matcher(lines.get(z));
                    if (matcherStart.find())
                    {
                        counter++;
                    }
                    z++;
                }

                findOneLineRoller(lines,valuesList,patternRollerLine,z);

                Matcher matcherStart = patternStart.matcher(lines.get(z));
                if (matcherStart.find())
                {
                    counter++;
                }

                gearsSizes.add(counter);
            }
        }
    }

    private void findOneLineRoller(List<String> lines, List<Integer> valuesList, Pattern patternLine, int z){

        Pattern patternOneLineRoller = Pattern.compile(".*[{].*}.*");
        Matcher matcherOneLineRoller = patternOneLineRoller.matcher(lines.get(z));

        if (matcherOneLineRoller.find())
        {
            searchLine(lines,valuesList,patternLine,z,4);
        }
        
    }

    private void searchGroup(List<String> lines, List<Integer> valuesList, Pattern patternGroup, int i, int size){

        this.valuesList = valuesList;

        int count = 1;

        for (int k = i; k < i + size; k++) {

            Matcher matcherGroup = patternGroup.matcher(lines.get(k));
            matcherGroup.find();
            valuesList.add(Integer.parseInt(matcherGroup.group(count)));

            count++;
        }
    }

    private void searchLine(List<String> lines, List<Integer> valuesList, Pattern patternLine, int i, int size){

        this.valuesList = valuesList;

        Matcher matcherLine = patternLine.matcher(lines.get(i));
        matcherLine.find();
        for (int k = 1; k <= size; k++ ){
            valuesList.add(Integer.parseInt(matcherLine.group(k)));
        }
    }

    void setInputPathFile(String inputPathFile){
        this.inputPathFile = inputPathFile;
    }

    void setRegexDrive(String regexDrive){
        this.regexDrive = regexDrive;
    }

    void setRegexRoller(String regexRoller){
        this.regexRoller = regexRoller;
    }

    void setCoordinateX(String coordinateX){
        this.coordinateX = coordinateX;
    }

    void setCoordinateY(String coordinateY){
        this.coordinateY = coordinateY;
    }

    void setBottomRadius(String bottomRadius){
        this.bottomRadius = bottomRadius;
    }

    void setTopRadius(String topRadius){
        this.topRadius = topRadius;
    }

    void setVelocity(String velocity){
        this.velocity = velocity;
    }

    List<Integer> getListOfAllValues(){
        return valuesList;
    }

    List<Integer> getListOfSizes(){
        return gearsSizes;
    }
}
