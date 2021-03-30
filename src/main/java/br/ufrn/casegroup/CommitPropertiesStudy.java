package br.ufrn.casegroup;

import java.util.ArrayList;
import java.util.List;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.CommitRange;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;

public class CommitPropertiesStudy implements Study{

    @Override
    public void execute() {
        List<String> commits = new ArrayList<String>();
        commits.add("a62e08f7b99584f3f4bbb171e30ff4e5e259fdf9"); //
        commits.add("ae9b33b977b1c73f93bb457bf0a939692e1f816c"); //
        commits.add("2d0981a2cd1c821f976d76ee3cf8d5a3dcb22f46"); //
        commits.add("d4ec9780f323cc52d80d92286016c8f6d4388550");
        commits.add("988a164ea60ffb660999b4e4e3a5f78abf5bcab4");
        commits.add("794b14c4b48bd9644ab556366178a85a48fdfe32"); //
        commits.add("4e1815286456fe70a06f1115f8e2b92206a15905");
        commits.add("f66cb811c65cfaab4fb07fbc3fa97991fdf2030d");
        commits.add("6315b7031d781a96ff09efcb6a2a8d39b6cf47c3");
        commits.add("29182b7fcedee0db0e8f7e945d104a348d97c7a7");
        commits.add("2e61e56efcc64249054bfebf0477b1323a764bc1");
        String project_name="bitshares/bitshares-core";

        new RepositoryMining()
            .in(GitRemoteRepository.hostedOn("https://github.com/"+project_name)
                .inTempDir("/home/elieziosoares/Doutorado/Causalidade/StudyRepos/"+project_name)
                .asBareRepos()
                .buildAsSCMRepository())
            .through(Commits.list(commits))
            .visitorsAreThreadSafe(true)
            .visitorsChangeRepoState(false)
            .withThreads(50)
            .process(new CommitsVisitor(), new CSVFile("/home/elieziosoares/Doutorado/Causalidade/data.csv"))
            .mine();
        ;
        
    }
    
}
