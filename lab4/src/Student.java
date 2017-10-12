public class Student {
    private String name;
    private int course;
    private int group;

    public Student(){
        name = "";
        course = 1;
        group = 1;
    }

    public Student(String name, int course, int group) throws Exception {
        setName(name);
        setCourse(course);
        setGroup(group);
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Student setCourse(int course) throws Exception {
        if (course < 1 || course > 4)
            throw new Exception("Bad course number.");
        this.course = course;
        return this;
    }

    public int getCourse() {
        return  this.course;
    }

    public Student setGroup(int group) throws Exception{
        if (group < 1)
            throw new Exception("Group number below 0.");
        this.group = group;
        return this;
    }

    public int getGroup() {
        return this.group;
    }

    @Override
    public String toString() {
        return name + ", course " + course + ", group " + group;
    }
}
