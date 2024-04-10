import java.io.Serializable;

/**
 * Created by emg2 on 3/12/2018.
 */
public class TourneyNode implements Serializable{

    private Team team;
    private Team loser;
    private double allProb;
    private double five38Prob;
    private double rankProb;
    private double vbProb;
    private double kenProb;

    public TourneyNode(){
        team = null;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public Team getLoser() {
        return loser;
    }

    public double getAllProb() {
        return allProb;
    }

    public double getFive38Prob() {
        return five38Prob;
    }

    public double getRankProb() {
        return rankProb;
    }

    public double getVbProb() {
        return vbProb;
    }

    public double getKenProb() {
        return kenProb;
    }

    public void setFive38Prob(double five38Prob) {
        this.five38Prob = five38Prob;
    }

    public void setVbProb(double vbProb) {
        this.vbProb = vbProb;
    }

    public void setKenProb(double kenProb) {
        this.kenProb = kenProb;
    }

    public void determineWinner(TourneyNode node1, TourneyNode node2, String choice){
        choice = choice.toLowerCase();
        double totalToAvg = 0.0;
        double numToAvg = 0.0;
        if (choice.contains("a")){
            choice = choice + "kfv";
        }
        if (choice.contains("k")){
            totalToAvg += kenProb;
            numToAvg += 1.0;
        }
        if (choice.contains("f")){
            totalToAvg += five38Prob;
            numToAvg += 1.0;
        }
        if (choice.contains("v")){
            totalToAvg += vbProb;
            numToAvg+= 1.0;
        }
        if (numToAvg < 0.01){
            totalToAvg+= kenProb + five38Prob + vbProb;
            numToAvg = 3.0;
        }
        allProb = totalToAvg / numToAvg;
        if (allProb > 0.5){
            team = node1.getTeam();
            loser = node2.getTeam();
        }
        else{
            team = node2.getTeam();
            loser = node1.getTeam();
            allProb = 1 - allProb;
        }

        if (choice.contains("r")){
            if (Math.random() > allProb){
                Team temp = team;
                team = loser;
                loser = temp;
                allProb = 1- allProb;
            }
        }
    }

    public double compareKen(TourneyNode other){
        double kenProbTemp = compFunc(team.getKenpomeff() - other.getTeam().getKenpomeff(), 70);
        return kenProbTemp;
    }

    public double compareVb(TourneyNode other){
        double vbProbTemp = compFunc(team.getVb() - other.getTeam().getVb(), 55);
        return vbProbTemp;
    }

    public double compare538(TourneyNode other){
        double five38ProbTemp = compFunc(team.getFive38num() - other.getTeam().getFive38num(), 30.464);
        return five38ProbTemp;
    }
    //Not useful right now
/*
    public double compareRank(TourneyNode other){
        double avgThis = (team.getBpi() + team.getRpi()) / 2.0;
        double avgOther = (other.getTeam().getBpi() + other.getTeam().getRpi()) / 2.0;
        rankProb = .5 + (avgThis - avgOther) * 0.01;
        if (rankProb < 0){
            rankProb = 0.001;
        }
        else if (rankProb > 1){
            rankProb = 0.999;
        }
        return rankProb;
    }
*/
    private double compFunc(double a, double scale){
        return 1.0/(1.0+Math.pow(10.0,-a*scale/400.0));
    }

}
