package com.akhilesh.learning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.util.List;

/**
 * Created by admin on 3/14/17.
 */
public class XmlToJsonConverter {

    final private static String IN_FILE = "/home/admin/idxm2_01mviulqdgxmfam0nxmj7xhyoc1xx3vd0qq_group_0.idx";
    final private static String OUT_FILE = "/tmp/jsonout";

    public static void main(String[] args) {
        //ConvertUsingJackson.invoke();
        ConvertUsingJSON.invoke();
    }

    private static class ConvertUsingJackson {
        private static void invoke() {
            XmlMapper xmlMapper = new XmlMapper();
            List entries = null;

            try {
                entries = xmlMapper.readValue(new File(IN_FILE), List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = null;
            try (final OutputStream outFile = new FileOutputStream(OUT_FILE)) {
                json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entries);
                jsonMapper.writerWithDefaultPrettyPrinter().writeValue(outFile, json);
                outFile.flush();
                System.out.println(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ConvertUsingJSON {
        private static void invoke() {
            int PRETTY_PRINT_INDENT_FACTOR = 4;
            try {
                final InputStream is = new FileInputStream(IN_FILE);
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));

                String line = buf.readLine();
                StringBuilder sb = new StringBuilder();

                while(line != null){
                    sb.append(line).append("\n");
                    line = buf.readLine();
                }
                JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
                String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
                System.out.println(jsonPrettyPrintString);
            } catch (JSONException je) {
                System.out.println(je.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
