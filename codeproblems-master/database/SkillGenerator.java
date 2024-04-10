import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class SkillGenerator {

    static ArrayList<Skill> skill;
    static ArrayList<Nwcet> nwcet;

    public static void main(String[] args) throws IOException{

        skill = new ArrayList<>();
        nwcet = new ArrayList<>();

        List<String> fileSkills = Files.readAllLines(Paths.get("skills.csv"));
        List<String> fileNwcet = Files.readAllLines(Paths.get("nwcet.csv"));
        String[][] dataSkills = new String[fileSkills.size()][5];
        String[][] dataNwcet = new String[fileNwcet.size()][3];

        for (int i = 0; i < fileSkills.size(); i++){
            dataSkills[i] = fileSkills.get(i).split(";");
        }
        for (int i = 0; i < fileNwcet.size(); i++){
            dataNwcet[i] = fileNwcet.get(i).split(";");
        }


        for(int i = 0; i <fileSkills.size(); i++){
            Skill newSkill = new Skill(dataSkills[i][0], dataSkills[i][1]);
            for (int j = 2; j < dataSkills[i].length; j++){
                if (dataSkills[i][j] != null)
                    newSkill.addNwcet(dataSkills[i][j]);
            }
            skill.add(newSkill);
        }

        for (int i = 0; i < fileNwcet.size(); i++){

            Nwcet newNwcet = new Nwcet(dataNwcet[i][0],
                    (dataNwcet[i][2].equals("X") ? dataNwcet[i][1] : dataNwcet[i][1] + "-"+dataNwcet[i][2]));
            nwcet.add(newNwcet);
        }

        Formatter outS = new Formatter("skills.sql");
        Formatter outN = new Formatter("nwcet.sql");
       // Formatter outC = new Formatter("skillcate.sql");

        for (Nwcet n:nwcet){
            if (!n.nwcet_code.contains("-")) {
                outN.format("INSERT INTO nwcet\n\tVALUES('%s','%s',null);\n", n.nwcet_code, n.title);
            }
            else{
                outN.format("INSERT INTO nwcet\n\tVALUES('%s','%s','%s');\n", n.nwcet_code, n.title, n.nwcet_code.split("-")[0]);
            }
        }

        for(Skill s:skill){

            if (s.nwcetCodes.isEmpty()) {
                outS.format("INSERT INTO knowledge_skill\n\tVALUES('%s','%s',null, null);\n", s.ks_code, s.title );
            }
            else{
                outS.format("INSERT INTO knowledge_skill\n\tVALUES('%s','%s',null, '%s');\n", s.ks_code, s.title, s.nwcetCodes.get(0) );
            }
            //for(String t:s.nwcetCodes){
            //    outC.format("INSERT INTO skill_cate\n\tVALUES('%s','%s');\n", s.ks_code, t);
            //}
        }

        //outC.close();
        outN.close();
        outS.close();

    }

    public static class Skill{

        String ks_code;
        String title;
        String description;
        ArrayList<String> nwcetCodes;

        public Skill(String ks_code, String title){
            this.ks_code = ks_code;
            this.title = title;
            this.description = null;
            nwcetCodes = new ArrayList<>();
        }

        public void addNwcet(String toAdd){
            nwcetCodes.add(toAdd);
        }

    }


    public static class Nwcet{
        String nwcet_code;
        String title;

        public Nwcet(String title, String nwcet_code){
            this.nwcet_code = nwcet_code;
            this.title = title;
        }
    }

}
