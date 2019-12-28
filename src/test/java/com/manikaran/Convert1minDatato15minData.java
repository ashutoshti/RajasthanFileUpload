package com.manikaran;

import Utilties.ExcelApi;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Convert1minDatato15minData {

    static File masterTimeBlock = new File(System.getProperty("user.dir") + "/src/test/DataFiles/MasterTimeBlock.txt"); // txt file path
    static File scadaFile = new File(System.getProperty("user.dir") + "/src/test/DataFiles/winddataawk.csv"); // csv file address

    static Double[] checkActualValue = null;
    static ExcelApi excelApi;
    static String excelCellTimeValue;
    static Float excelCellValue;
    static String temp;
    static File outputfile = new File(System.getProperty("user.dir") + "/src/test/DataFiles/ConvertedFile.xlsx");
    static final int GENERTOR_START_FROM_INDEX = 2;
    static final int GENERTOR_END_INDEX = 80;

    public static void main(String[] args) {
        averageOfCell();
    }

    public static void averageOfCell() {
        ExcelApi excelapi = null;
        try {
            excelapi = new ExcelApi(outputfile);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Map<String, Integer> masterHashMap = new HashMap<String, Integer>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(masterTimeBlock));
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String st, stScada;
        try {
            while ((st = br.readLine()) != null) {
                String str[] = st.split(",");
                masterHashMap.put(str[0], Integer.valueOf(str[1]));
            }
        } catch (NumberFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // System.out.println("============= Master data map================");
        for (int k = GENERTOR_START_FROM_INDEX; k <= GENERTOR_END_INDEX; k++) {
            Map<Integer, Calculation> compareHashMap = new HashMap<Integer, Calculation>();
            int i = 0;
            System.out.println("Value of k " + k);
            try {
                BufferedReader brScada = new BufferedReader(new FileReader(scadaFile));
                while ((stScada = brScada.readLine()) != null) {
                    // System.out.println(stScada);
                    i++;
                    String str[] = stScada.split(" ");
                    String timeSc = str[0];
                    Float scada = Float.valueOf(str[k]);
                    if (masterHashMap.containsKey(timeSc.trim())) {
                        Integer blockNo = masterHashMap.get(timeSc.trim());
                        if (compareHashMap.containsKey(blockNo)) {
                            Calculation cal = compareHashMap.get(blockNo);
                            cal.setTotal(cal.getTotal() + scada);
                            cal.setNoOfOccurance(cal.getNoOfOccurance() + 1);
                        } else {
                            Calculation cal2 = new Calculation();
                            cal2.setNoOfOccurance(1);
                            cal2.setTotal(scada);
                            compareHashMap.put(blockNo, cal2);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("error at line no=" + i);
                System.out.println(e);
            }

            for (Map.Entry<Integer, Calculation> entry : compareHashMap.entrySet()) {
                Integer blk = entry.getKey();
                Calculation value = entry.getValue();
                System.out.println(
                        "Average By 15 min Per Block " + blk + " " + value.getTotal() / value.getNoOfOccurance());
                excelapi.setCellData("Sheet1", k, blk, String.valueOf(value.getTotal() / value.getNoOfOccurance()));

            }

        }
    }
}

class Calculation {
    private Float total;
    private Integer noOfOccurance;

    public Float getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Calculation [total=" + total + ", noOfOccurance=" + noOfOccurance + "]";
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getNoOfOccurance() {
        return noOfOccurance;
    }

    public void setNoOfOccurance(Integer noOfOccurance) {
        this.noOfOccurance = noOfOccurance;
    }

}

