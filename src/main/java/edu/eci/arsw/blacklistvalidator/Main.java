package edu.eci.arsw.blacklistvalidator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String ipAddress = "202.24.34.55";
        HostBlackListsValidator validator = new HostBlackListsValidator();

        int cores = Runtime.getRuntime().availableProcessors();

        int[] threadCounts = {1, cores, cores * 2, 50, 100};

        for (int nThreads : threadCounts) {
            System.out.println("\n=== Ejecutando con " + nThreads + " hilos ===");

            long startTime = System.currentTimeMillis();

            List<Integer> blackListOccurrences = validator.checkHost(ipAddress, nThreads);

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("La dirección IP " + ipAddress +
                    " fue encontrada en " + blackListOccurrences + " listas negras.");
            System.out.println("Tiempo de ejecución: " + duration + " ms");
        }
    }
}
