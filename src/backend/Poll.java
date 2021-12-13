/***
 * This class which is part of the backend, helps in setting up the poll functionality and things related to polls
 * @author Team 043
 * @version 12/13/2021
 *
 */
package backend;

import java.io.Serializable;
import java.util.ArrayList;

public class Poll implements Serializable {
    private ArrayList<Integer> pollResults;
    private ArrayList<String> pollOptions;
    private ArrayList<User> pollUsers;

    public Poll(ArrayList<Integer> pollResults, ArrayList<String> pollOptions) {
        this.pollResults = pollResults;
        this.pollOptions = pollOptions;
    }

    public Poll(ArrayList<Integer> pollResults, ArrayList<String> pollOptions, ArrayList<User> pollUsers) {
        this.pollResults = pollResults;
        this.pollOptions = pollOptions;
        this.pollUsers = pollUsers;
    }

    public Poll() {
        pollResults = new ArrayList<Integer>();
        pollOptions = new ArrayList<String>();
        pollUsers = new ArrayList<User>();
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


    public void addPollUser(User pollUser) {
        pollUsers.add(pollUser);
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

    public boolean addPollVote(int option, User user) {
        if (pollUsers.contains(user)) return false;
        pollResults.set(option, pollResults.get(option) + 1);
        pollUsers.add(user);
        return true;
    }

    public boolean subtractPollVote(int option, User user) {
        if (!pollUsers.contains(user)) return false;
        pollResults.set(option, pollResults.get(option) - 1);
        pollUsers.remove(user);
        return true;
    }

    public String toString() {
        String out = "Poll\n";
        for (String option : pollOptions) out += String.format("pollOption:%s\n", option);
        for (int result : pollResults) out += String.format("pollResult:%s\n", result);
        for (User user : pollUsers) out += String.format("pollUser:%s\n", user.getUsername());
        out += "END OF POLL\n";
        return out;
    }
}   
