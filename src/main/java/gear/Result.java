package gear;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

class Result {

    private Gear[] gears;
    private int test;
    private String error1;
    private String error2;
    private String indent = "  "; //2 spaces

    Result() {}

    void check(Gear[] gears) {

        this.gears = gears;

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
                    gears[j].setDirection("rolka w spoczynku");
                }
            }
        }

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
                        error2 = "konflikt predkosci obrotowych rolek";
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
                        error2 = "konflikt predkosci obrotowych rolek";
                    }
                }
                if (sumBottomRadius(i, j) > distance(i, j) || sumTopRadius(i, j) > distance(i, j)) {
                    test = 1;
                    error1 = "nakladajace sie tarcze";
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



    void allWriteJson (String fileName, int systemLength, int j) throws IOException{
        Writer decorWriter = null;
        try {decorWriter = new BufferedWriter(new FileWriter(fileName, true));
            //zapisuje w json glowna klase i odwoluje sie do writeFile
            if (j == 0){
                decorWriter.append("{");
                decorWriter.append("\n"+repeatString(indent,1)+"\"uklady\": [");
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
        finally {
            if ( decorWriter!= null){
                decorWriter.close();
            }
        }
    }

    private void writeFile(String fileName, int systemLength, int j) throws IOException {

        Writer writer = null;
        try  { writer = new BufferedWriter(new FileWriter(fileName, true));

            //zapisuje bledy
            writer.append("\n"+repeatString(indent,2)+"{");
            if (test != 0) {
                if (error1 != null) {
                    writer.append("\n"+repeatString(indent,3)+"\"blad\": \""+error1+"\"");
                } else if (error2 != null) {
                    writer.append("\n"+repeatString(indent,3)+"\"blad\": \""+error2+"\"");
                }
            }
            // zapisuje obiekt z tablica rolek (kierunki i predkosci lub ostrzezenie)
            else {
                writer.append("\n"+repeatString(indent,3)+"\"rolki\": [");

                for (int i = 0; i < gears.length; i++) {

                    if (gears[i].velocity != 0) {
                        writer.append("\n"+repeatString(indent,4)+"{ ");
                        writer.append("\n"+repeatString(indent,5)+"\"k\": \""+gears[i].direction+"\",");
                        writer.append("\n"+repeatString(indent,5)+"\"n\": " + gears[i].velocity);
                    } else {
                        writer.append("\n"+repeatString(indent,4)+"{ ");
                        writer.append("\n"+repeatString(indent,5)+ "\"ostrzezenie\": \""
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
        finally {
            if (writer != null){
                writer.close();
            }
        }
    }

    private String repeatString(String s,int count){
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < count; i++) {
            r.append(s);
        }
        return r.toString();
    }
}
