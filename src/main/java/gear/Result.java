package gear;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

class Result {

    private Gear[] gears;
    private int test;
    private String errorOverlapping;
    private String errorVelocity;
    private String indent = "  "; //2 spaces

    Result() {}

    void check(Gear[] gears) {

        this.gears = gears;

        //count velocity and set value of direction
        for (int i = 0; i < gears.length - 1; i++) {
            for (int j = i + 1; j < gears.length; j++) {

                if (sumBottomRadius(i,j) == distance(i,j) || sumTopRadius(i,j) == distance(i,j)) {
                    if (sumBottomRadius(i,j) == distance(i,j)) {
                        double v = (gears[i].velocity * gears[i].bottomRadius) / gears[j].bottomRadius;
                        gears[j].setVelocity(v);
                    }
                    if (sumTopRadius(i,j) == distance(i,j)) {
                        double v = (gears[i].velocity * gears[i].topRadius) / gears[j].topRadius;
                        gears[j].setVelocity(v);
                    }

                    if (gears[i].direction.equals("R")) {
                        gears[j].setDirection("L");
                    } else {
                        gears[j].setDirection("R");
                    }
                }
                if (gears[j].direction == null) {
                    gears[j].setDirection("roller at rest"); //roll at rest
                }
            }
        }

        //if gear is incorrect set info about error
        for (int i = 0; i < gears.length - 1; i++) {
            for (int j = i + 1; j < gears.length; j++) {

                double velocityRatio = gears[i].velocity / gears[j].velocity;

                if (sumBottomRadius(i,j) == distance(i,j)) {
                    double ratioD = gears[j].bottomRadius / gears[i].bottomRadius;

                    ratioD *= 100;
                    ratioD = Math.round(ratioD);

                    velocityRatio *= 100;
                    velocityRatio = Math.round(velocityRatio);

                    if (velocityRatio != ratioD) {
                        test = 1;
                        errorVelocity = "roller velocity conflict";
                    }
                }
                if (sumTopRadius(i,j) == distance(i,j)) {
                    double ratioG = gears[j].topRadius / gears[i].topRadius;

                    ratioG *= 100;
                    ratioG = Math.round(ratioG);

                    velocityRatio *= 100;
                    velocityRatio = Math.round(velocityRatio);

                    if (velocityRatio != ratioG) {
                        test = 1;
                        errorVelocity = "roller velocity conflict";
                    }
                }
                if (sumBottomRadius(i, j) > distance(i, j) || sumTopRadius(i, j) > distance(i, j)) {
                    test = 1;
                    errorOverlapping = "overlapping wheels";
                }
            }
        }
    }

    private double distance(int i, int j){

        double xDifference = gears[i].coordinateX - gears[j].coordinateX;
        double yDifference = gears[i].coordinateY - gears[j].coordinateY;
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    private double sumBottomRadius(int i, int j){
        return gears[i].bottomRadius + gears[j].bottomRadius;
    }

    private double sumTopRadius(int i, int j){
        return gears[i].topRadius + gears[j].topRadius;
    }

    //method to create outer class in json file which contains table of gears
    void allWriteJson (String fileName, int systemLength, int j) throws IOException{

        try (Writer decorWriter = new BufferedWriter(new FileWriter(fileName, true))) {

            if (j == 0){
                decorWriter.append("{");
                decorWriter.append("\n"+repeatString(indent,1)+"\"gears\": ["); //"gears":
                decorWriter.flush();
            }

            writeFile(fileName,systemLength,j);

            if (j == systemLength - 1){
                decorWriter.append("\n"+repeatString(indent,1)+"]");
                decorWriter.append("\n}");
                decorWriter.append("\n");
                decorWriter.flush();
            }
        }
        catch (Exception e){
            System.out.println("error in allWriteJson"+e);
        }

    }

    private void writeFile(String fileName, int systemLength, int j) throws IOException {


        try  (Writer writer = new BufferedWriter(new FileWriter(fileName, true))) {

            //show error message in json file
            writer.append("\n"+repeatString(indent,2)+"{");
            if (test != 0) {
                if (errorOverlapping != null) {
                    writer.append("\n"+repeatString(indent,3)+"\"error\": \""+errorOverlapping+"\"");
                } else if (errorVelocity != null) {
                    writer.append("\n"+repeatString(indent,3)+"\"error\": \""+errorVelocity+"\"");
                }
            }

            // create gear table with velocity and direction of each roll or show warning "roller at rest"
            else {
                writer.append("\n"+repeatString(indent,3)+"\"rollers\": [");

                for (int i = 0; i < gears.length; i++) {

                    if (gears[i].velocity != 0) {
                        writer.append("\n"+repeatString(indent,4)+"{ ");
                        writer.append("\n"+repeatString(indent,5)+"\"k\": \""+gears[i].direction+"\",");
                        writer.append("\n"+repeatString(indent,5)+"\"n\": " + gears[i].velocity);
                    } else {
                        writer.append("\n"+repeatString(indent,4)+"{ ");
                        writer.append("\n"+repeatString(indent,5)+ "\"warning\": \""
                                +gears[i].direction+"\"");
                    }

                    if (i == gears.length-1){
                        writer.append("\n"+repeatString(indent,4)+"}");
                    }
                    else {
                        writer.append("\n"+repeatString(indent,4)+"},");
                    }
                }

                writer.append("\n"+repeatString(indent,3)+"] ");
            }
            if (j == systemLength - 1){
                writer.append("\n"+repeatString(indent,2)+"} ");
            }
            else {
                writer.append("\n"+repeatString(indent,2)+"}, ");
            }
        }
        catch (Exception e){
            System.err.println("error in writeFile" + e);
        }

    }

    private String repeatString(String s,int count){
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < count; i++) {
            r.append(s);
        }
        return r.toString();
    }

    String getErrorOverlapping() {
        return errorOverlapping;
    }

    String getErrorVelocity() {
        return errorVelocity;
    }
}
