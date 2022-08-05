/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package football_championship_manager;

import java.util.Date;

/**
 *
 * @author Tomasz Dawid Wasowski
 */
public class FootballMatch {
    
    //<editor-fold defaultstate="collapsed" desc="Fields">
    
    private FootballClub firstClub;
    private FootballClub secondClub;
    
    private int firstClubScore;
    private int secondClubScore;
    
    private Date matchDate;
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    
    //This constructor assigns every field of the match and calls the 
    //resolveMatch method
    public FootballMatch(FootballClub firstClub, FootballClub secondClub,
            int firstClubScore, int secondClubScore, Date matchDate) {

        setFirstClub(firstClub);
        setSecondClub(secondClub);
        setFirstClubScore(firstClubScore);
        setSecondClubScore(secondClubScore);
        setMatchDate(matchDate);
        
        resolveMatch();
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Set Methods">
    
    public void setFirstClub(FootballClub firstClub) {
        this.firstClub = firstClub;
    }
    
    public void setSecondClub(FootballClub secondClub) {
        this.secondClub = secondClub;
    }
    
    public void setFirstClubScore(int firstClubScore) {
        this.firstClubScore = firstClubScore;
    }
    
    public void setSecondClubScore(int secondClubScore) {
        this.secondClubScore = secondClubScore;
    }
    
    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get Methods">
    
    public FootballClub getFirstClub() {
        return firstClub;
    }
    
    public FootballClub getSecondClub() {
        return secondClub;
    }
    
    public int getFirstClubScore() {
        return firstClubScore;
    }
    
    public int getSecondClubScore() {
        return secondClubScore;
    }
    
    public Date getMatchDate() {
        return matchDate;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Class Methods">
    
    //Resolves the match by calculating and updating all necessary statistics
    private void resolveMatch() {

        //Updates wins, losses and draws based on who won the game
        if (firstClubScore > secondClubScore) {
            firstClub.incrementWins();
            secondClub.incrementDefeats();
        } else if (secondClubScore > firstClubScore) {
            secondClub.incrementWins();
            firstClub.incrementDefeats();
        } else {
            firstClub.incrementDraws();
            secondClub.incrementDraws();
        }
        
        //Updates goals scored and recieved for the first club
        firstClub.increaseGoalsScored(firstClubScore);
        firstClub.increaseGoalsReceived(secondClubScore);

        //Updates goals scored and recieved for the second club
        secondClub.increaseGoalsScored(secondClubScore);
        secondClub.increaseGoalsReceived(firstClubScore);

        //Updates the GD, PTs and MP stats for both clubs
        firstClub.updateStats();
        secondClub.updateStats();       
    }
    
//</editor-fold>
    
}
