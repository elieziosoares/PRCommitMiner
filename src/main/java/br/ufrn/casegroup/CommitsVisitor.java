package br.ufrn.casegroup;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

import com.thoughtworks.xstream.converters.extended.SqlDateConverter;

import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import br.ufrn.casegroup.DAO.AbsCommitDAO;
import br.ufrn.casegroup.DAO.CommitDAO;
import br.ufrn.casegroup.DAO.MergeCommitDAO;


public class CommitsVisitor implements CommitVisitor{
	AbsCommitDAO commitDAO;

    public CommitsVisitor(AbsCommitDAO commitDAO) {
		this.commitDAO = commitDAO;
	}

	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		System.out.println("+ Visiting commit "+commit.getHash());

		br.ufrn.casegroup.Domain.Commit commitobj = new br.ufrn.casegroup.Domain.Commit(commit.getHash());

		int added = 0;
		int removed = 0;
		//int lines = 0;
		int files = 0;
		int test_files = 0;
		int test_lines = 0;
		int commit_size = 0;
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		for(Modification m : commit.getModifications()) {
			files ++;
			added += m.getAdded();
			removed += m.getRemoved();
			
			commit_size += m.getAdded()+m.getRemoved();

			if(isTestFile(m.getFileName())){
				test_files++;
				test_lines += m.getAdded()+m.getRemoved();
				/*try {
					Writer arquivo = new BufferedWriter(new FileWriter("/home/elieziosoares/Doutorado/Causalidade/data.txt", true));
					arquivo.append("\n"+m.getFileName());
					arquivo.close();
				}catch (IOException e) {
					System.err.println("Writing test file error.");
				}*/
			}			
		}

		commitobj.setDate(commit.getDate().getTime());
		commitobj.setMainBranch(commit.isInMainBranch());
		commitobj.setMerge(commit.isMerge());
		commitobj.setDeletions(removed);
		commitobj.setInsertions(added);
		commitobj.setSize(commit_size);
		commitobj.setFiles(files);
		commitobj.setTestVolume(test_lines);
		commitobj.setTest_files(test_files);

		commitobj.setMsg(commit.getMsg());
		//commitobj.setAuthor_id(commit.getAuthor().);

		commitDAO.updateCommit(commitobj);
		System.out.println("\n   ..."+ commitobj.toString());
		/*try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}*/
	}

	private boolean isTestFile(String file) {
		if (file.contains("_test") || file.contains("test_") || file.contains("Test_") || file.contains("_Test") || file.contains("_TEST") || file.contains("_TEST")) 
			if(!file.contains("latest") && !file.contains("LATEST"))
				return true;
		return false;
	}
}

/*

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
			sdt.format(commit.getDate().getTime()),
			commit.isInMainBranch(),
			commit.isMerge(),
			added,
			removed,
			files,
			test_lines,
			commit_size
		);


		//commit.getModifications()

		*/
