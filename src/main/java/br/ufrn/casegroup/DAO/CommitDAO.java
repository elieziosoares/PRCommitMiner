package br.ufrn.casegroup.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.casegroup.Domain.Commit;

public class CommitDAO extends AbsCommitDAO{
    private Connection conn;

    public CommitDAO() throws SQLException {
        //this.conn = ConnectionFactory.getConnection();
        this.conn = DBCPDataSource.getConnection();
    }

    public List<Commit> getCommitsToMine(String project_name){
        List<Commit> commits = new ArrayList<Commit>();
        String selectCommits = "SELECT C.commit_sha FROM commits C INNER JOIN commit_PR P ON C.commit_sha = P.commit_sha WHERE C.commit_size is NULL and P.project_name like ?";
        
        try
        {
            if (conn.isClosed())
                this.conn = ConnectionFactory.getConnection();    
            
            PreparedStatement stm = this.conn.prepareStatement(selectCommits);
            
            stm.setString(0, project_name);

            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                Commit commit = new Commit(rs.getString("commit_sha"));   
                commits.add(commit);
            }

            //rs.close();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("getCommitsToMine - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }
        return commits;      
   }

    public List<String> getCommitsToMine_sha(String project_name){
        List<String> commits = new ArrayList<String>();
        //String selectCommits = "SELECT C.commit_sha FROM commits C INNER JOIN commit_PR P ON C.commit_sha = P.commit_sha WHERE P.project_name like ?";
        String selectCommits = "SELECT C.commit_sha FROM commits C INNER JOIN commit_PR P ON C.commit_sha = P.commit_sha INNER JOIN PULLREQUESTS PR ON P.pr_number = PR.pr_number AND P.project_name = PR.project_name WHERE P.project_name like ? and C.commit_size is null and PR.inPeriodRQ1 is true;";
        try
        {
            if (conn.isClosed())
                this.conn = ConnectionFactory.getConnection();    
            
            PreparedStatement stm = this.conn.prepareStatement(selectCommits);

            stm.setString(1, project_name);

            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                commits.add(rs.getString("commit_sha"));
            }

            //rs.close();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("getCommitsToMine_sha - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }
        return commits;  
    }

    public void updateCommit(Commit commit) {
        String updateCommit = "UPDATE COMMITS SET COMMIT_SIZE = ?, TEST_VOLUME = ?, MERGE = ?, DELETIONS = ?, INSERTIONS = ?, LINES = ?, FILES = ?, TEST_FILES = ? WHERE COMMIT_SHA LIKE ?";
        
        try
        {
            if (conn.isClosed())
                this.conn = ConnectionFactory.getConnection();    
            
            PreparedStatement stm = this.conn.prepareStatement(updateCommit);

            stm.setInt(1, commit.getSize());
            stm.setInt(2, commit.getTestVolume());
            stm.setBoolean(3, commit.isMerge());
            stm.setInt(4, commit.getDeletions());
            stm.setInt(5, commit.getInsertions());
            stm.setInt(6, commit.getLines());
            stm.setInt(7, commit.getFiles());
            stm.setInt(8, commit.getTest_files());
            stm.setString(9, commit.getSha());

            stm.executeUpdate();
            //stm.close();
            //conn.close();
        } catch(SQLException e) {
            System.err.print("updateCommit - Transaction was not well succeeded.");
            System.err.print(e.getMessage());
        }
    }
    
}
