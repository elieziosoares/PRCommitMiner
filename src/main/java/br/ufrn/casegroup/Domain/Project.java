package br.ufrn.casegroup.Domain;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.casegroup.DAO.CommitDAO;

public class Project {
    private String repo_name;
    private String repo_url;
    private String travis_url;
    private boolean mined;
    private List<Commit> commits;
    private List<String> commits_sha;
    
    public Project() {}
    
    
    public Project(String repo_name, String repo_url){
        this.setRepo_name(repo_name);
        this.setRepo_url(repo_url);
    }

    public List<String> getCommits_sha() {
       
        if(this.commits == null){
            CommitDAO commitDAO = new CommitDAO();
            setCommits_sha(commitDAO.getCommitsToMine_sha(this.repo_name));
        }
        
        return this.commits_sha;
    }

    public void setCommits_sha(List<String> commits_sha) {
        this.commits_sha = commits_sha;
    }
    public List<Commit> getCommits() {
        if(this.commits == null){
            CommitDAO commitDAO = new CommitDAO();
            setCommits(commitDAO.getCommitsToMine(this.repo_name));
        }
        
        return this.commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public boolean isMined(){
        return mined;
    }

    public void setMined(boolean mined){
        this.mined = mined;
    }

    public String getTravis_url(){
        return travis_url;
    }

    public void setTravis_url(String travis_url){
        this.travis_url = travis_url;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }    
}
