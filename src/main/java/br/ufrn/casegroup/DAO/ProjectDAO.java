package br.ufrn.casegroup.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.casegroup.Domain.Project;

public class ProjectDAO {
    private Connection conn;

    public ProjectDAO() throws SQLException {
        //this.conn = ConnectionFactory.getConnection();
        this.conn = DBCPDataSource.getConnection();
    }

    public List<Project> getProjects(){
        List<Project> projects = new ArrayList<Project>();        
        String selectProjects = "SELECT * FROM PROJECTS WHERE commits_mined is TRUE and comments_mined is TRUE;";//" and commits_mined2 is not True";
        
        try(PreparedStatement stm = this.conn.prepareStatement(selectProjects);)
        {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Project proj = new Project(rs.getString("repo_name"),rs.getString("repo_url"));   
                projects.add(proj);
            }

            rs.close();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("getProjects - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }
        return projects;       
   }

   public List<Project> getProjectsToMergeCommits(){
        List<Project> projects = new ArrayList<Project>();        
        String selectProjects = "SELECT * FROM PROJECTS WHERE commits_mined2 is True and merge_commits_mined is False";
        
        try(PreparedStatement stm = this.conn.prepareStatement(selectProjects);)
        {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Project proj = new Project(rs.getString("repo_name"),rs.getString("repo_url"));   
                projects.add(proj);
            }

            rs.close();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("getProjects - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }
        return projects;       
    }

    public void updateProject_setMined(Project project) {
        String updateCommit = "UPDATE PROJECTS SET commits_mined2 = TRUE WHERE repo_name LIKE ?";

        
        try{
            if (conn.isClosed())
                this.conn = ConnectionFactory.getConnection();    
            
            PreparedStatement stm = this.conn.prepareStatement(updateCommit);
            stm.setString(1, project.getRepo_name());

            stm.executeUpdate();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("updateProject_setMined - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }
    }

    public void updateProject_setMinedMerge(Project project) {
        String updateCommit = "UPDATE PROJECTS SET merge_commits_mined = TRUE WHERE repo_name LIKE ?";
    
        
        try{
            if (conn.isClosed())
                this.conn = ConnectionFactory.getConnection();    
            
            PreparedStatement stm = this.conn.prepareStatement(updateCommit);
            stm.setString(1, project.getRepo_name());
    
            stm.executeUpdate();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("updateProject_setMined - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }
    }
}


