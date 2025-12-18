package model;

public class Fan {
    private int id;
    private String name;
    private String team;
    private int p1, p2, p3;

    public Fan(String name, String team, int p1, int p2, int p3) {
        this.name = name;
        this.team = team;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Fan(int id, String name, String team, int p1, int p2, int p3) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public double getAverage() {
        return (p1 + p2 + p3) / 3.0;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getTeam() { return team; }
    public int getP1() { return p1; }
    public int getP2() { return p2; }
    public int getP3() { return p3; }
}
