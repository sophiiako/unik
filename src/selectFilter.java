import java.util.List;


public class selectFilter {
    public boolean onlyWithDocumentation;
    public boolean tested;
    public boolean notTested;
    public List<String> usingPlatforms;
    public fwDate dateMin;
    public fwDate dateMax;
    public boolean onlyRolling;
    public boolean onlyMaster;
    public boolean allBranches;
    public String ownBranch;
    public boolean onlyAnotherBranches;


    public selectFilter(List<String> platforms) {
        onlyWithDocumentation = false;
        tested = true;
        notTested = true;
        setAllBranches();
        usingPlatforms = platforms;
        toDefaultDateInterval();
    }

    public void toDefaultDateInterval() {
        dateMin = (new fwDate("")).getDateMin();
        dateMax = (new fwDate("").getDateMax());
    }

    public void setOnlyMaster() {
        onlyMaster = true;
        onlyAnotherBranches = false;
        onlyRolling = false;
        ownBranch = "";
        allBranches = false;
    }

    public void setOnlyRolling() {
        onlyMaster = false;
        onlyRolling = true;
        onlyAnotherBranches = false;
        ownBranch = "";
        allBranches = false;
    }

    public void setOnlyAnotherBranches() {
        onlyMaster = false;
        onlyRolling = false;
        onlyAnotherBranches = true;
        ownBranch = "";
        allBranches = false;
    }

    public void setOwnBranch(String newBranch) {
        onlyMaster = false;
        onlyRolling = false;
        onlyAnotherBranches = false;
        ownBranch = newBranch;
        allBranches = false;
    }

    public void setAllBranches() {
        onlyMaster = false;
        onlyRolling = false;
        onlyAnotherBranches = false;
        ownBranch = "";
        allBranches = true;
    }

}

