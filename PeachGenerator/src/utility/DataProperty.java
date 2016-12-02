package utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Macintosh_HD on 10/09/2016.
 */
public class DataProperty {

    public static HashMap<String,Integer> getEnumFrequency(String[] EnumData){
        HashMap<String,Integer> returnHashMap = new HashMap<>();
        HashSet<String> enumVals = new HashSet<>();

        for(String val: EnumData ){
            enumVals.add(val);
        }

        for(String value: enumVals){
            int freq = Collections.frequency(Arrays.asList(EnumData),value);
            returnHashMap.put(value,freq);
        }

        return returnHashMap;
    }

}
