package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;

public class HostBlackListThread extends Thread {

    private int startIndex;
    private int endIndex;
    private String ipAddress;
    private HostBlacklistsDataSourceFacade skds;
    private List<Integer> occurrences;
    private int checkedLists;

    public HostBlackListThread(int startIndex, int endIndex, String ipAddress, HostBlacklistsDataSourceFacade skds) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipAddress = ipAddress;
        this.skds = skds;
        this.occurrences = new LinkedList<>();
        this.checkedLists = 0;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            checkedLists++;
            if (skds.isInBlackListServer(i, ipAddress)) {
                occurrences.add(i);
            }
        }
    }

    public List<Integer> getOccurrences() {
        return occurrences;
    }

    public int getCheckedLists() {
        return checkedLists;
    }
}