package br.ufrn.casegroup;

import java.text.SimpleDateFormat;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;


public class CommitsVisitor implements CommitVisitor{

    @Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

		int added = 0;
		int removed = 0;
		//int lines = 0;
		int files = 0;
		int test_lines = 0;
		int commit_size = 0;
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		for(Modification m : commit.getModifications()) {
			files ++;
			added += m.getAdded();
			removed += m.getRemoved();
			commit_size += m.getAdded()+m.getRemoved();

			if(m.getFileName().contains("test")){
				test_lines += m.getAdded()+m.getRemoved();
				writer.write(
					m.getFileName(),
					test_lines
				);
			}
			else
				writer.write(m.getFileName());
			
		}
		
		writer.write(
			commit.getHash(),
			commit.getCommitter().getName(),
			sdt.format(commit.getDate()),
			commit.isInMainBranch(),
			commit.isMerge(),
			added,
			removed,
			files,
			test_lines,
			commit_size
		);


		//commit.getModifications()

	}
}
