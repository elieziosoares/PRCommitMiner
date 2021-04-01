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

    public ProjectDAO() {
        this.conn = ConnectionFactory.getConnection();
    }

    public List<Project> getProjects(){
        List<Project> projects = new ArrayList<Project>();
        String selectProjects = "SELECT * FROM PROJECTS WHERE MINED = FALSE;";
        
        try(PreparedStatement stm = this.conn.prepareStatement(selectProjects);)
        {
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Project proj = new Project(rs.getString("repo_name"),rs.getString("repo_url"));   
                projects.add(proj);
            }

            rs.close();
            stm.close();
            conn.close();
        } catch(SQLException e) {

            if( this.conn != null){
                try {
                    System.err.print("getProjects - Transaction was not well succeeded.");
                    System.err.print(e.getMessage());
                    conn.close();
                } catch (SQLException excep) {
                    System.err.print("getProjects - Connection close fail.");
                    System.err.print(excep.getMessage());
                }
            }
        }
        return projects;       
   }

   public void updateProject_setMined(Project project) {
    String updateCommit = "UPDATE PROJECTS SET MINED = TRUE WHERE repo_name LIKE ?";

    
    try{
        if (conn.isClosed())
            this.conn = ConnectionFactory.getConnection();    
        
        PreparedStatement stm = this.conn.prepareStatement(updateCommit);
        stm.setString(1, project.getRepo_name());

        stm.executeUpdate();
        stm.close();
        conn.close();
    } catch(SQLException e) {
        if( this.conn != null){
            try {
                System.err.print("updateProject_setMined - Transaction was not well succeeded.");
                System.err.print(e.getMessage());
                conn.close();
            } catch (SQLException excep) {
                System.err.print("updateProject_setMined - Connection close fail.");
                System.err.print(excep.getMessage());
            }
        }
    }
}
}
