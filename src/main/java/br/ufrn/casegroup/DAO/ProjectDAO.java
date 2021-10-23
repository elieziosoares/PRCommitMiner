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

    public ProjectDAO(){}

    public List<Project> getProjects(){
        List<Project> projects = new ArrayList<Project>();        
        String selectProjects = "SELECT * FROM PROJECTS WHERE commits_mined is TRUE and comments_mined is TRUE;";//" and commits_mined2 is not True";
        
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try
        {
            conn = DBCPDataSource.getConnection();    
            stm = conn.prepareStatement(selectProjects);

            rs = stm.executeQuery();
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
        }finally {
            try { if (rs != null) rs.close(); } catch(Exception e) { }
            try { if (stm != null) stm.close(); } catch(Exception e) { }
            try { if (conn != null) conn.close(); } catch(Exception e) { }
        }
        return projects;       
   }

   public List<Project> getProjectsToMergeCommits(){
        List<Project> projects = new ArrayList<Project>();        
        String selectProjects = "SELECT * FROM PROJECTS WHERE commits_mined2 is True and merge_commits_mined is False";
        
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try
        {
            conn = DBCPDataSource.getConnection();    
            stm = conn.prepareStatement(selectProjects);

            rs = stm.executeQuery();
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
        }finally {
            try { if (rs != null) rs.close(); } catch(Exception e) { }
            try { if (stm != null) stm.close(); } catch(Exception e) { }
            try { if (conn != null) conn.close(); } catch(Exception e) { }
        }
        return projects;       
    }

    public void updateProject_setMined(Project project) {
        String updateCommit = "UPDATE PROJECTS SET commits_mined2 = TRUE WHERE repo_name LIKE ?";

        
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try
        {
            conn = DBCPDataSource.getConnection();    
            stm = conn.prepareStatement(updateCommit);
            stm.setString(1, project.getRepo_name());

            stm.executeUpdate();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("updateProject_setMined - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }finally {
            try { if (rs != null) rs.close(); } catch(Exception e) { }
            try { if (stm != null) stm.close(); } catch(Exception e) { }
            try { if (conn != null) conn.close(); } catch(Exception e) { }
        }
    }

    public void updateProject_setMinedMerge(Project project) {
        String updateCommit = "UPDATE PROJECTS SET merge_commits_mined = TRUE WHERE repo_name LIKE ?";
    
        
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try
        {
            conn = DBCPDataSource.getConnection();    
            stm = conn.prepareStatement(updateCommit);
            stm.setString(1, project.getRepo_name());
    
            stm.executeUpdate();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("updateProject_setMined - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }finally {
            try { if (rs != null) rs.close(); } catch(Exception e) { }
            try { if (stm != null) stm.close(); } catch(Exception e) { }
            try { if (conn != null) conn.close(); } catch(Exception e) { }
        }
    }
}


