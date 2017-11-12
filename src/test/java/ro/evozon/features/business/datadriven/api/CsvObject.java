package ro.evozon.features.business.datadriven.api;

import ro.evozon.tools.utils.fileutils.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvObject<T> {
    private   List<T> items;
    public CsvObject(List<T> items){
        this.items =items;
    }
    public  void writeToCsvFile(FileWriter writer, List<String> headingList, List<T> myObjList) {
        String line="";
        List<String > lineValuesList;
        try {

            CSVUtils.writeLine(writer, headingList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  myObjList.stream().forEach(f -> System.out.println("at last run " + f.getLocatieId()));
        for (int i = 0; i < myObjList.size(); i++) {
            line = myObjList.get(i).toString();
            String[] lineValuesArray = line.split(",");
            lineValuesList = new ArrayList<String>(Arrays.asList(lineValuesArray));
            try {
                CSVUtils.writeLine(writer, lineValuesList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
