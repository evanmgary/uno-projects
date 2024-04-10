import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by emg2 on 3/12/2018.
 */
public class TourneyTree implements Serializable{

    private TourneyNode[] matches;
    private HashMap<String, Team> teams;

    public TourneyTree(){
        matches = new TourneyNode[128];
        for (int i = 0; i < 128; i++){
            matches[i] = new TourneyNode();
        }
        teams = new HashMap<>();
    }

    public void addTeam(String name, int region, int seed, double five38num){
        teams.put(name, new Team(name, region, seed, five38num));
    }
    public void setKenpom(String name, double d){
        teams.get(name).setKenpomeff(d);
    }

    public void setRpiBpi(String name, double rpi, double bpi){
        teams.get(name).setRpi(rpi);
        teams.get(name).setBpi(bpi);
    }

    public void setVb(String name, double d){
        teams.get(name).setVb(d);
    }

    public Team getTeam(String name){
        return teams.get(name);
    }

    public void addNode(int index, int region, int seed){
        matches[index] = new TourneyNode();
    }

    public void buildLeaves(){

        for(Team t:teams.values()){
            int seed = t.getSeed();
            int region = t.getRegion() - 1;
            switch(seed){
                case 1:
                    matches[63+(1+region*16)].setTeam(t);
                    break;
                case 2:
                    matches[63+(15+region*16)].setTeam(t);
                    break;
                case 3:
                    matches[63+(9+region*16)].setTeam(t);
                    break;
                case 4:
                    matches[63+(5+region*16)].setTeam(t);
                    break;
                case 5:
                    matches[63+(7+region*16)].setTeam(t);
                    break;
                case 6:
                    matches[63+(11+region*16)].setTeam(t);
                    break;
                case 7:
                    matches[63+(13+region*16)].setTeam(t);
                    break;
                case 8:
                    matches[63+(3+region*16)].setTeam(t);
                    break;
                case 9:
                    matches[63+(4+region*16)].setTeam(t);
                    break;
                case 10:
                    matches[63+(14+region*16)].setTeam(t);
                    break;
                case 11:
                    matches[63+(12+region*16)].setTeam(t);
                    break;
                case 12:
                    matches[63+(8+region*16)].setTeam(t);
                    break;
                case 13:
                    matches[63+(6+region*16)].setTeam(t);
                    break;
                case 14:
                    matches[63+(10+region*16)].setTeam(t);
                    break;
                case 15:
                    matches[63+(16+region*16)].setTeam(t);
                    break;
                case 16:
                    matches[63+(2+region*16)].setTeam(t);
                    break;
                default:
                    break;

            }
        }
    }

    public String doMatch(int index, String choice){

        TourneyNode node1 = matches[index*2];
        TourneyNode node2 = matches[index*2 + 1];
        double kpScore = node1.compareKen(node2);
        matches[index].setKenProb(kpScore);
        double five38Score = node1.compare538(node2);
        matches[index].setFive38Prob(five38Score);
        double vbScore = node1.compareVb(node2);
        matches[index].setVbProb(vbScore);
        matches[index].determineWinner(node1, node2, choice);
        return String.format("%s %.3f",matches[index].getTeam().getName(), matches[index].getAllProb());
    }

}
