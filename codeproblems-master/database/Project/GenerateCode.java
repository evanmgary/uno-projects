package Project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.List;

/**
 * This is the code generator that takes our large query file, parses it, and makes java code that runs the queries.
 * See Queries.java for the result.
 * @author Evan Gary
 */
public class GenerateCode {

    public static void main(String[] args) throws IOException {

        List<String> file = Files.readAllLines(Paths.get("queries.sql"));
        Formatter out = new Formatter("./Project/Queries.java");

        out.format("package Project;\nimport java.sql.*;\n");
        out.format("public class Queries{\n\n");
        out.format("\tprivate Connection conn;\n");
        out.format("\tpublic Queries(Connection conn) throws SQLException{\n\t\tthis.conn = conn;\n\t}\n");

        int pos = 0;
        int numParam = 0;
        while(pos < file.size()){

            pos++;
            try {
                file.get(pos);
            }
            catch(IndexOutOfBoundsException e){
                break;
            }
            numParam = 0;
            String[] params = file.get(pos).split(" ");
            pos++;

            out.format("\tpublic ResultSet query%s(", file.get(pos));
            if(params[0].length() > 0){
                out.format("%s param1", params[0]);
                numParam++;
            }
            if(params.length > 1){
                out.format(", %s param2",params[1]);
                numParam++;
            }
            if(params.length > 2){
                out.format(", %s param3",params[2]);
                numParam++;
            }
            out.format(") throws SQLException{\n\t\tStringBuilder query = new StringBuilder();\n");

            pos++;
            while(!file.get(pos).contains("~")){
                out.format("\t\tquery.append(\"%s \\n\");\n",file.get(pos));
                pos++;
                if (pos >= file.size() - 1){
                    break;
                }
            }
            //out.format("\t\tquery.append(\";\");\n");
            out.format("\t\tPreparedStatement ps = conn.prepareStatement(query.toString());\n");
            if (numParam > 0){
                out.format("\t\tps.set%s(1, param1);\n", params[0].equals("String") ? "String" : "Int");
            }
            if (numParam > 1){
                out.format("\t\tps.set%s(2, param2);\n", params[1].equals("String") ? "String" : "Int");
            }
            if (numParam > 2){
                out.format("\t\tps.set%s(3, param3);\n", params[2].equals("String") ? "String" : "Int");
            }
            out.format("\t\tResultSet rs = ps.executeQuery();\n");
            out.format("\t\treturn rs;\n");
            out.format("\t}\n\n");
        }
        out.format("}");
        out.close();
    }
}
