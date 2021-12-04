package backend;
import java.util.ArrayList;

public class Poll {
    private ArrayList<Integer> pollResults;
    private ArrayList<String> pollOptions; 

    public Poll(ArrayList<Integer> pollResults, ArrayList<String> pollOptions) {
        this.pollResults = pollResults;
        this.pollOptions = pollOptions;
    }

    public Poll(){
        pollResults = new ArrayList<Integer>();
        pollOptions = new ArrayList<String>();
    }

    public ArrayList<String> getPollOptions() {
        return pollOptions;
    }

    public void addPollOption(String pollOption) {
        pollOptions.add(pollOption);
        pollResults.add(0);
    }

    public void addPollOption(String pollOption, boolean addPollResult) {
        pollOptions.add(pollOption);
        if (addPollResult) pollResults.add(0);
    }

    public ArrayList<Integer> getPollResults() {
        return pollResults;
    }

    public void addPollResult(int result) {
        pollResults.add(result);
    }

    public void addPollVote(int option) {
        pollResults.set(option, pollResults.get(option) + 1);
    }

    public void subtractPollVote(int option) {
        pollResults.set(option, pollResults.get(option) - 1);
    }

    public String toString() {
        String out = "Poll\n";
        for (String option : pollOptions) out += String.format("pollOption:%s\n", option);
        for (int result : pollResults) out += String.format("pollResult:%s\n", result);
        out += "END OF POLL\n";
        return out;
    }
}   
