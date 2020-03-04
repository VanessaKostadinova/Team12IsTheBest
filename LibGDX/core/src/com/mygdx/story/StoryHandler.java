package com.mygdx.story;

import com.mygdx.extras.PermanetPlayer;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class StoryHandler {

    private StoryHandler storyHandler = null;

    //Tutorial
    public Boolean foundAllNotesAboutDisease = false;
    public Boolean curedHouseDisease = false;
    public Boolean diseaseReappears = false;
    public Boolean investigateHouse = false;

    //Part 1 - of story
    public Boolean findTracesOfDoctor = false;
    public Boolean speakToOrder = false;
    public Boolean found4Notes = false;
    public Boolean found6Notes = false;
    public Boolean goToChurch = false;
    public Boolean findFiveMoreNotes = false;
    public Boolean findAnotherDoctor = false;
    public Boolean talkToDoctor = false;




    public String getStageOfStory() {
        if(!tutorialDone()) {
            if(!foundAllNotesAboutDisease) {
                return "Find initial notes!";
            }
            if(!curedHouseDisease) {
                return "Need to cure the house disease!";
            }
            if(!diseaseReappears) {
                return "Disease reappeared!";
            }
            if(!investigateHouse) {
                return "Investigate House";
            }
        }
        return "Tutorial Done!";

    }

    public Boolean didSpreadKnowledgeOfConspiracy() {
        return talkToDoctor;
    }

    private Boolean part2Done() {
        return (findTracesOfDoctor &&
                speakToOrder &&
                found6Notes &&
                goToChurch &&
                findFiveMoreNotes &&
                findAnotherDoctor &&
                talkToDoctor);
    }

    private  Boolean tutorialDone() {
        return (foundAllNotesAboutDisease &&
                curedHouseDisease &&
                diseaseReappears &&
                investigateHouse);
    }

    public StoryHandler getInstance() {
        if(storyHandler == null) {
            storyHandler = new StoryHandler();
        }
        return storyHandler;
    }
}
