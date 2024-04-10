package Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;

public class GenerateQueryScripts {

    public static void main(String[] args) throws IOException{
        List<String> file = Files.readAllLines(Paths.get("queries.sql"));
        int pos = 2;
        while(true){

            try{
                file.get(pos);
            }
            catch (IndexOutOfBoundsException e){
                break;
            }
            pos++;
            Formatter out = new Formatter(file.get(pos)+".sql");
            pos++;

            while(!file.get(pos).contains("~")){
                out.format(file.get(pos) +"\n");
                pos++;
                if (pos >= file.size() - 1){
                    break;
                }
            }
            out.close();
            pos++;

        }

    }
}
