import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

public class CateGenerator {

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

        List<String> file = Files.readAllLines(Paths.get("soc_structure_2010.csv"));
        String[][] data = new String[file.size()][7];

        for (int i = 13; i < file.size(); i++){
            data[i] = file.get(i).split(";");
        }

        for(int i = 13; i < file.size(); i++){
            if (!data[i][0].equals("")){
                all.put(data[i][0],new Category(data[i][0],data[i][4], data[i][5],1));
            }
            else if (!data[i][1].equals("")){
                all.put(data[i][1],new Category(data[i][1],data[i][4],data[i][5],2));
            }
            else if (!data[i][2].equals("")){
                all.put(data[i][2],new Category(data[i][2],data[i][4],data[i][5],3));
            }
            else if (!data[i][3].equals("")){
                all.put(data[i][3],new Category(data[i][3],data[i][4],data[i][5],4));
            }
        }
        Formatter out = new Formatter("job_category.sql");

        for(Category c:all.values()){
            if (c.parent_cate != null) {
                out.format("INSERT INTO job_category\n\tVALUES('%s','%s','%s','%s','%s');\n", c.cate_code, c.title, c.description, c.parent_cate, c.isLeaf);
            }
            else{
                out.format("INSERT INTO job_category\n\tVALUES('%s','%s','%s', null, '%s');\n", c.cate_code, c.title, c.description, c.isLeaf);

            }
        }

        /*
        for(Category c:all.values()){
            if (c.parent_cate != null) {
                out.format("INSERT INTO parent_cate\n\tVALUES('%s','%s');\n", c.cate_code, c.parent_cate);
            }
        }
        */
        out.close();
    }

    private static class Category{
        private String lev1;
        private String lev2;
        private String lev3;
        private String lev4;
        private int topLevel;
        private String isLeaf;

        private String cate_code;
        private String parent_cate;
        private String title;
        private String description;

        private Category(String cate_code, String title, String description, int topLevel){
            this.cate_code = cate_code;
            this.title = title;
            this.topLevel = topLevel;
            lev1 = cate_code.substring(0,2);
            lev2 = cate_code.substring(3,4);
            lev3 = cate_code.substring(4,6);
            lev4 = cate_code.substring(6,7);
            addToHashmap();
            determineParent();
            this.description = description;
        }

        private void addToHashmap(){
            switch(topLevel){
                case 1:
                    isLeaf = "no";
                    level1.put(lev1, this);
                    break;
                case 2:
                    isLeaf = "no";
                    level2.put(cate_code.substring(0,4), this);
                    break;
                case 3:
                    isLeaf = "no";
                    level3.put(cate_code.substring(0,6), this);
                    break;
                case 4:
                    isLeaf = "yes";
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
                    parent_cate = level1.get(lev1).cate_code;
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
