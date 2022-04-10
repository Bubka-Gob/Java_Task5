package fileWorkers;

import models.Field;
import models.Figure;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Saver {

    public static void saveBinary(String name) throws IOException {
        String path = "saves/" + name + ".save";
        File newFile = new File(path);
        newFile.createNewFile();
        FileOutputStream fileOutput = new FileOutputStream(path);
        ObjectOutputStream objectOutput =new ObjectOutputStream(fileOutput);

        for(Figure figure: Field.getFigures()) {
            objectOutput.writeObject(figure);
        }

        objectOutput.flush();
        objectOutput.close();
    }

    public static List<Figure> loadBinary(String name) throws IOException {
        List<Figure> figureList = new ArrayList<>();
        FileInputStream fileInput = new FileInputStream("saves/" + name + ".save");
        ObjectInputStream objectInput = new ObjectInputStream(fileInput);

        while(true) {
            try {
                figureList.add((Figure) objectInput.readObject());
            } catch (Exception e) {
                break;
            }
        }

        return figureList;
    }

}
