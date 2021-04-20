
package br.ufrn.casegroup.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.casegroup.Domain.Commit;

public class MergeCommitDAO extends AbsCommitDAO{
    private Connection conn;

    public MergeCommitDAO() {
        this.conn = ConnectionFactory.getConnection();
    }

    public List<Commit> getCommitsToMine(String project_name){
        List<Commit> commits = new ArrayList<Commit>();
        String selectCommits = "SELECT C.commit_sha FROM merge_commits C INNER JOIN pullrequests P ON C.commit_sha = P.merge_commit_sha WHERE C.commit_size is NULL and P.project_name like ?";
        
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

            rs.close();
            stm.close();
            conn.close();
        } catch(SQLException e) {
            if( this.conn != null){
                try {
                    System.err.print("getCommitsToMine - Transaction was not well succeeded.");
                    System.err.print(e.getMessage());
                    conn.close();
                } catch (SQLException excep) {
                    System.err.print("getCommitsToMine - Connection close fail.");
                    System.err.print(excep.getMessage());
                }
            }
        }
        return commits;      
    }

    public List<String> getCommitsToMine_sha(String project_name){
        List<String> commits = new ArrayList<String>();
        //String selectCommits = "SELECT C.commit_sha FROM merge_commits C INNER JOIN pullrequests P ON C.commit_sha = P.merge_commit_sha WHERE C.commit_size is NULL and P.project_name like ?";
        String selectCommits = "SELECT C.commit_sha FROM merge_commits C INNER JOIN pullrequests P ON C.commit_sha = P.merge_commit_sha WHERE P.project_name like ?";
        
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

            rs.close();
            stm.close();
            conn.close();
        } catch(SQLException e) {
            if( this.conn != null){
                try {
                    System.err.print("getCommitsToMine_sha - Transaction was not well succeeded.");
                    System.err.print(e.getMessage());
                    conn.close();
                } catch (SQLException excep) {
                    System.err.print("getCommitsToMine_sha - Connection close fail.");
                    System.err.print(excep.getMessage());
                }
            }
        }
        return commits;  
    }

    public void updateCommit(Commit commit) {
        String updateCommit = "UPDATE MERGE_COMMITS SET COMMIT_SIZE = ?, TEST_VOLUME = ?, IN_MAIN_BRANCH = ?, DELETIONS = ?, INSERTIONS = ?, LINES = ?, FILES = ?, TEST_FILES = ?, COMMIT_DATE = ?, MSG = ? WHERE COMMIT_SHA LIKE ?";
        System.out.println(" ¨¨¨Updating commit " + commit.getSha());
        try
        {
            if (conn.isClosed())
                this.conn = ConnectionFactory.getConnection();    
            
            PreparedStatement stm = this.conn.prepareStatement(updateCommit);

            stm.setInt(1, commit.getSize());
            stm.setInt(2, commit.getTestVolume());
            stm.setBoolean(3,commit.isMainBranch());
            stm.setInt(4, commit.getDeletions());
            stm.setInt(5, commit.getInsertions());
            stm.setInt(6, commit.getLines());
            stm.setInt(7, commit.getFiles());
            stm.setInt(8, commit.getTest_files());
            stm.setDate(9, new java.sql.Date(commit.getDate().getTime()));
            stm.setString(10, commit.getMsg());
            stm.setString(11, commit.getSha());

            stm.executeUpdate();
            stm.close();
            conn.close();
        } catch(SQLException e) {
            if( this.conn != null){
                try {
                    System.err.print("updateCommit - Transaction was not well succeeded. - Exception: " +e.getMessage());
                    conn.close();
                    Thread.sleep(1000);
                    System.out.println("¨¨¨ retry same commit after error: " + commit.getSha());
                    updateCommit(commit);
                } catch (SQLException excep) {
                    System.err.print("updateCommit - Connection close fail.");
                    System.err.print(excep.getMessage());
                } catch (InterruptedException e2) {
                    System.err.print("updateCommit - Sleep to retry failure.");
                    System.err.print(e2.getMessage());
                }
            }
        }
    }
    
}
