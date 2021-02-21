import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GradeRegister {

    private final ArrayList<Integer> grades;
    private final ArrayList<Integer> points = new ArrayList<>();

    public GradeRegister() {
        this.grades = new ArrayList<>();
    }

    public void addGradeBasedOnPoints(int points) {
        this.grades.add(pointsToGrade(points));
        this.points.add(points);
    }

    public int numberOfGrades(int grade) {
        int count = 0;
        for (int received : this.grades) {
            if (received == grade) {
                count++;
            }
        }

        return count;
    }

    public void getPointsAverage() {
        final double pointsAverage = points.stream()
                .mapToDouble(p -> p)
                .average()
                .orElse(-1);

        System.out.println("Point average (all): " + pointsAverage);
    }

    public int pointsToGrade(int points) {
        int grade;
        if (points < 50) {
            grade = 0;
        } else if (points < 60) {
            grade = 1;
        } else if (points < 70) {
            grade = 2;
        } else if (points < 80) {
            grade = 3;
        } else if (points < 90) {
            grade = 4;
        } else {
            grade = 5;
        }

        return grade;
    }

    public void printGradeDistribution() {
        int grade = 5;
        System.out.println("Pass percentage: " + getPassPercentage());
        while (grade >= 0) {
            int stars = numberOfGrades(grade);
            System.out.print(grade + ": ");
            printsStars(stars);
            System.out.println("");

            grade = grade - 1;
        }
    }

    public void printsStars(int stars) {
        while (stars > 0) {
            System.out.print("*");
            stars--;
        }
    }

    public void printPassingPointsAverage() {
        final double average = points.stream()
                .mapToDouble(p -> p)
                .filter(p -> p >= 50)
                .average()
                .orElse(-1);

        String result = "Point average (passing): ";
        result += average != -1 ? Double.valueOf(average) : "-";

        System.out.println(result);
    }

    public double getPassPercentage() {
        final List<Integer> passingGrades = points.stream()
                .filter(p -> p >= 50)
                .collect(Collectors.toList());

        return 100.0 * passingGrades.size() / points.size();
    }

}