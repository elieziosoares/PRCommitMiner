package br.ufrn.casegroup.DAO;

import java.util.List;
import br.ufrn.casegroup.Domain.Commit;

public abstract class AbsCommitDAO {
    public abstract void updateCommit(Commit commit);
    public abstract List<String> getCommitsToMine_sha(String project_name);
    public abstract List<Commit> getCommitsToMine(String project_name);
}
