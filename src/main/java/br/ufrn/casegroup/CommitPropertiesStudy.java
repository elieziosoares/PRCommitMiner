package br.ufrn.casegroup;

import java.util.List;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.core.util.FileUtils;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.scm.GitRemoteRepository;

import br.ufrn.casegroup.DAO.CommitDAO;
import br.ufrn.casegroup.DAO.ProjectDAO;
import br.ufrn.casegroup.Domain.Project;

public class CommitPropertiesStudy implements Study{

    private ProjectDAO projectDAO;
    List<Project> projects;

    @Override
    public void execute() {
        projectDAO = new ProjectDAO();
        projects = projectDAO.getProjects();

        for (Project project : projects) {
            System.out.println("\n===============================================================================");
            System.out.println(String.format("### Projeto: %s \t\t- URL: %s  ", project.getRepo_name(),project.getRepo_url()));
        
            new RepositoryMining()
            .in(GitRemoteRepository.hostedOn(project.getRepo_url())
                .inTempDir("/home/elieziosoares/Doutorado/Causalidade/StudyRepos/"+project.getRepo_name())
                //.asBareRepos()
                .buildAsSCMRepository())
            .through(Commits.list(project.getCommits_sha(new CommitDAO())))
            .visitorsAreThreadSafe(true)
            .visitorsChangeRepoState(false)
            .withThreads(20)
            .process(new CommitsVisitor(new CommitDAO()))//, new CSVFile("/home/elieziosoares/Doutorado/Causalidade/data.csv"))
            .mine();
        
            project.setCommits_sha(null);
            projectDAO.updateProject_setMined(project);
            System.out.println("Mining of project" +project.getRepo_name()+ " finished.");
        }

       /* try {
            FileUtils.deleteDirectory(new File("/home/elieziosoares/Doutorado/Causalidade/StudyRepos"));
        } catch (IOException e) {
            System.err.println("Couldn't delete repoTmpDir: " + e);
        }*/
    }
    
    
}
