package br.ufrn.casegroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.scm.GitRemoteRepository;

import br.ufrn.casegroup.DAO.MergeCommitDAO;
import br.ufrn.casegroup.DAO.ProjectDAO;
import br.ufrn.casegroup.Domain.Project;

public class MergeCommitsPropertiesStudy implements Study{

    private ProjectDAO projectDAO;
    List<Project> projects;
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");     

    @Override
    public void execute() {
        projectDAO = new ProjectDAO();
        projects = projectDAO.getProjects();

        for (Project project : projects) {

            System.out.println("\n===============================================================================\n" + dtf.format(LocalDateTime.now()));
            System.out.println(String.format("### Projeto: %s \t\t- URL: %s  ", project.getRepo_name(),project.getRepo_url()));
        
            new RepositoryMining()
            .in(GitRemoteRepository.hostedOn(project.getRepo_url())
                .inTempDir("/home/elieziosoares/Doutorado/Causalidade/StudyRepos/"+project.getRepo_name())
                //.asBareRepos()
                .buildAsSCMRepository())
            .through(Commits.list(project.getCommits_sha(new MergeCommitDAO())))
            .visitorsAreThreadSafe(true)
            .visitorsChangeRepoState(false)
            .withThreads(20)
            .process(new CommitsVisitor(new MergeCommitDAO()))//, new CSVFile("/home/elieziosoares/Doutorado/Causalidade/data.csv"))
            .mine();
        
            project.setCommits_sha(null);
            projectDAO.updateProject_setMined(project);
            System.out.println(dtf.format(LocalDateTime.now()) + " - Mining of project" +project.getRepo_name()+ " finished.");
        }

    }
}