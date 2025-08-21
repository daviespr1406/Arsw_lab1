package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT = 5;
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @param N number of threads to use
     * @return Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int N) {

        LinkedList<Integer> blackListOcurrences = new LinkedList<>();
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

        int totalServers = skds.getRegisteredServersCount();
        int segmentSize = totalServers / N;

        HostBlackListThread[] threads = new HostBlackListThread[N];
        int start = 0;

        for (int i = 0; i < N; i++) {
            int end = (i == N - 1) ? totalServers : start + segmentSize;
            threads[i] = new HostBlackListThread(start, end, ipaddress, skds);
            threads[i].start();
            start = end;
        }
        int totalChecked = 0;
        for (int i = 0; i < N; i++) {
            try {
                threads[i].join();
                blackListOcurrences.addAll(threads[i].getOccurrences());
                totalChecked += threads[i].getCheckedLists();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (blackListOcurrences.size() >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
            LOG.log(Level.INFO, "HOST {0} Reported as NOT trustworthy", ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
            LOG.log(Level.INFO, "HOST {0} Reported as trustworthy", ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{totalChecked, totalServers});

        return blackListOcurrences;
    }
}


