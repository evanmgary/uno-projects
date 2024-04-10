import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by emg2 on 3/12/2018.
 * Generates the tournament tree and serializes it.
 */
public class Parser {
    private static List<String> kenpom;
    private static List<String> rankings;
    private static List<String> five38;
    private static List<String> vb;


    public static void readData(){
        try{
            kenpom = Files.readAllLines(Paths.get("kenpom.csv"));
            rankings = Files.readAllLines(Paths.get("rankings.csv"));
            five38 = Files.readAllLines(Paths.get("538.csv"));
            vb = Files.readAllLines(Paths.get("vb.csv"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void generateTeams(TourneyTree t){
        for (int i = 0; i < 68; i++){
            String team538 = five38.get(i);
            String[] teamSplit = team538.split(",");
            t.addTeam(teamSplit[0], Integer.parseInt(teamSplit[2]), Integer.parseInt(teamSplit[3]), Double.parseDouble(teamSplit[1]));
        }
        for(int i = 0; i < 68; i++){
            String[] teamKen = kenpom.get(i).split(",");
            String[] teamVb = vb.get(i).split(",");
            String[] teamRank = rankings.get(i).split(",");
            t.setKenpom(teamKen[0], Double.parseDouble(teamKen[1]));
            t.setVb(teamVb[0], Double.parseDouble(teamVb[1]));
            t.setRpiBpi(teamRank[0], Double.parseDouble(teamRank[1]), Double.parseDouble(teamRank[2]));
        }
    }

    public static void buildTournament(TourneyTree t){
        t.buildLeaves();

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("blank.ser"))){
            out.writeObject(t);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


}
