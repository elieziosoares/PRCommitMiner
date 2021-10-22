package br.ufrn.casegroup;

import org.repodriller.*;

public final class App{
    private App() {
    }

    public static void main(String[] args) {
        System.out.println("=== === === === Initializing mining with RepoDriller === === === ===");
        //new RepoDriller().start(new MergeCommitsPropertiesStudy());
        new RepoDriller().start(new CommitPropertiesStudy());

    }
}