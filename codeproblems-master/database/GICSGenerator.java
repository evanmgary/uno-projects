import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

public class GICSGenerator {

    static HashMap<String, Category> all;
    static HashMap<String, Category> level1;
    static HashMap<String, Category> level2;
    static HashMap<String, Category> level3;
    static HashMap<String, Category> level4;

    public static void main(String[] args) throws IOException{

        all = new HashMap<>();
        level1 = new HashMap<>();
        level2 = new HashMap<>();
        level3 = new HashMap<>();
        level4 = new HashMap<>();

        List<String> file = Files.readAllLines(Paths.get("GICS.csv"));
        String[][] data = new String[file.size()][8];

        for (int i = 5; i < file.size(); i++){
            data[i] = file.get(i).split(";");
        }

        for(int i = 5; i < file.size(); i = i + 2){
            if (!data[i][0].equals("")){
                all.put(data[i][0],new Category(data[i][0],data[i][1], null,1));
            }
            if (!data[i][2].equals("")){
                all.put(data[i][2],new Category(data[i][2],data[i][3], null,2));
            }
            if (!data[i][4].equals("")){
                all.put(data[i][4],new Category(data[i][4],data[i][5],null,3));
            }
            if (!data[i][6].equals("")){
                all.put(data[i][6],new Category(data[i][6],data[i][7],data[i+1][7],4));
            }
        }
        Formatter out = new Formatter("gics.sql");

        for(Category c:all.values()){
            out.format("INSERT INTO gics\n\tVALUES('%s','%s','%s');\n",c.cate_code,c.title,c.description);
        }
        for(Category c:all.values()){
            if (c.parent_cate != null) {
                out.format("INSERT INTO parent_gics\n\tVALUES('%s','%s');\n", c.cate_code, c.parent_cate);
            }
        }

        out.close();
    }

    private static class Category{

        private int topLevel;

        private String cate_code;
        private String parent_cate;
        private String title;
        private String description;

        private Category(String cate_code, String title, String description, int topLevel){
            this.cate_code = cate_code;
            this.title = title;
            this.topLevel = topLevel;
            addToHashmap();
            determineParent();
            this.description = description;
        }

        private void addToHashmap(){
            switch(topLevel){
                case 1:
                    level1.put(cate_code, this);
                    break;
                case 2:
                    level2.put(cate_code, this);
                    break;
                case 3:
                    level3.put(cate_code, this);
                    break;
                case 4:
                    level4.put(cate_code, this);
                    break;
            }
        }

        private void determineParent(){
            switch(topLevel){
                case 1:
                    parent_cate = null;
                    break;
                case 2:
                    parent_cate = level1.get(cate_code.substring(0,2)).cate_code;
                    break;
                case 3:
                    parent_cate = level2.get(cate_code.substring(0,4)).cate_code;
                    break;
                case 4:
                    parent_cate = level3.get(cate_code.substring(0,6)).cate_code;
                    break;
            }
        }

    }

}
