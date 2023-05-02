package mz.ac.luis.seia.finacieme.helper;

import java.text.SimpleDateFormat;

public class DataCustom {

    public static String currentData(){
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataString = simpleDateFormat.format(data);
            return  dataString;
    }
}
