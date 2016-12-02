package model;

/*
 * ARX: Powerful Data Anonymization
 * Copyright 2012 - 2016 Fabian Prasser, Florian Kohlmayer and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import external.utility.ARXBaseClass;
import org.apache.commons.io.FilenameUtils;
import org.deidentifier.arx.*;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.criteria.KAnonymity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class ARXkAnonymiser extends ARXBaseClass {

    public static ArrayList<String[]> kAnonimiseData(File csvFile, kAnonData[] kAnonRecords, int kAnon) throws IOException {

        /* Check file is of correct type / !null */
        if(csvFile == null || !FilenameUtils.getExtension(csvFile.getAbsolutePath()).equals("csv")){
            System.out.println("ARXkAnon : invalid file");
            return null;
        }

        /* Check kAnonData !null */
        if(kAnonRecords == null ){
            System.out.println("ARXkAnon : invalid records (null) ");
            return null;
        }

        /* Check valid input */
        if(kAnon <= 0){
            System.out.println("ARXkAnon : invalid k Anon value");
            return null;
        }

        //return Arr;
        String[][] outputArr;

        // Define data
        Data data = Data.create(csvFile, StandardCharsets.UTF_8,',');

        //create arraylist of hierarchies
        Hierarchy[] hierarchies = new Hierarchy[kAnonRecords.length];

        // Define hierarchies
        //Hierarchy age = Hierarchy.create(ageHeirarchyFile,StandardCharsets.UTF_8,',');

        Hierarchy recordHierarchy;
        AttributeType attribute;

        for(kAnonData record: kAnonRecords){

            //create the Attribute type:
            switch (record.getAttributeType()){
                //"Identifier","Quasi-Identifier","Sensitive","Insensitive"
                case "Identifier":
                    attribute = AttributeType.IDENTIFYING_ATTRIBUTE;
                    break;
                case "Quasi-Identifier":
                    attribute = AttributeType.QUASI_IDENTIFYING_ATTRIBUTE;
                    break;
                case "Sensitive":
                    attribute = AttributeType.SENSITIVE_ATTRIBUTE;
                    break;
                case "Insensitive":
                    attribute = AttributeType.INSENSITIVE_ATTRIBUTE;
                    break;
                default:
                    attribute = AttributeType.IDENTIFYING_ATTRIBUTE;
            }

            if(record.getAnonHierarchy() != null){
                recordHierarchy = Hierarchy.create(record.getAnonHierarchy(),StandardCharsets.UTF_8,',');
                data.getDefinition().setAttributeType(record.getattributeTitle(),recordHierarchy);
            } else {
                data.getDefinition().setAttributeType(record.getattributeTitle(),attribute);
            }


        }

        // Create an instance of the anonymizer
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXConfiguration config = ARXConfiguration.create();
        config.addCriterion(new KAnonymity(kAnon));
        config.setMaxOutliers(1.0d);

        ARXResult result = anonymizer.anonymize(data, config);

        // Print info
        printResult(result, data);

        ArrayList<String[]> outputArrList = new ArrayList<>();

        // Process results
        //System.out.println(" - Transformed data:");
        Iterator<String[]> transformed = result.getOutput(false).iterator();
        while (transformed.hasNext()) {
            //System.out.print("   ");
            String[] transformedData = transformed.next();
            //System.out.println(Arrays.toString(transformedData));
            outputArrList.add(transformedData);
        }

        return outputArrList;

    }

}