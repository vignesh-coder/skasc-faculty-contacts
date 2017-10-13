package in.ac.skasc.skascfacultycontacts;

class Department {

    private String id, deptName;

    Department() {
    }

    Department(String id, String deptName) {
        this.id = id;
        this.deptName = deptName;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getDeptName() {
        return deptName;
    }

    void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override

    public String toString() {
        return getDeptName();
    }
}