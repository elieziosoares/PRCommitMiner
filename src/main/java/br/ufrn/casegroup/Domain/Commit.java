package br.ufrn.casegroup.Domain;

import java.util.Date;

public class Commit {
    private String sha;
    private Date date;
    private int size;
    private int testVolume;
    private String msg;
    private int author_id;
    private boolean mainBranch;
    private boolean merge;
    private String path;
    private int deletions;
    private int insertions;
    private int lines;
    private int files;
    private int test_files;

    public Commit(){}
    public Commit(String sha) {
        this.sha = sha;
    }
    public Commit(String sha, int size, int testVolume, boolean mainBranch, boolean merge, int deletions, int insertions,
            int files) {
        this.sha = sha;
        this.size = size;
        this.testVolume = testVolume;
        this.mainBranch = mainBranch;
        this.merge = merge;
        this.deletions = deletions;
        this.insertions = insertions;
        this.files = files;
    }

    public int getTest_files() {
        return test_files;
    }
    public void setTest_files(int test_files) {
        this.test_files = test_files;
    }
    
    public String getSha() {
        return sha;
    }
    public int getFiles() {
        return files;
    }
    public void setFiles(int files) {
        this.files = files;
    }
    public int getLines() {
        return lines;
    }
    public void setLines(int lines) {
        this.lines = lines;
    }
    public int getInsertions() {
        return insertions;
    }
    public void setInsertions(int insertions) {
        this.insertions = insertions;
    }
    public int getDeletions() {
        return deletions;
    }
    public void setDeletions(int deletions) {
        this.deletions = deletions;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public boolean isMerge() {
        return merge;
    }
    public void setMerge(boolean merge) {
        this.merge = merge;
    }
    public boolean isMainBranch() {
        return mainBranch;
    }
    public void setMainBranch(boolean mainBranch) {
        this.mainBranch = mainBranch;
    }
    public int getAuthor_id() {
        return author_id;
    }
    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getTestVolume() {
        return testVolume;
    }
    public void setTestVolume(int test_volume) {
        this.testVolume = test_volume;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setSha(String sha) {
        this.sha = sha;
    }

    
    @Override
    public String toString(){
        return "SHA "+this.sha+ "; Size: " +this.size+ "; Test Vol. " +this.testVolume+ "; Main Branch?" +mainBranch+ 
        "; Merge? " +merge+ "; Deletions: " +deletions+ "; Insertions: " +insertions+ "; Files: " +files;
    }
}
