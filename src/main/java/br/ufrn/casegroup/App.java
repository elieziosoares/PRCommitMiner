package br.ufrn.casegroup;

import org.repodriller.*;

public final class App{
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("=== === === === Initializing mining with RepoDriller === === === ===");
        new RepoDriller().start(new CommitPropertiesStudy());
    }
}
