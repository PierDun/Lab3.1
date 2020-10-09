package sample;

public class IterationMethodForSystem {

    String firstFuncStr;
    String secondFuncStr;

    private double x1;
    private double x2;
    double acc;

    int counter;

    public String solve(){
        double prevX1 = 0;
        double prevX2 = 0;
        counter = 0;

        while ((Math.abs(x1 - prevX1) > acc || Math.abs(x2 - prevX2) > acc) && counter < 3000000) {
            prevX1 = x1;
            prevX2 = x2;

            x1 = getSecondFunction(prevX1, prevX2);
            x2 = getFirstFunction(x1);
            counter++;
        }

        return "Координаты: (" + x1 + "; " + x2 + ")\nКоличество итераций: " + counter;
    }

    double getFirstFunction(double x1) {
        switch (firstFuncStr) {
            case "x1^2 + 25x1 - x2 = 0":
                return x1 * x1 + 25 * x1;
            case "x1^3 + 23x1 - x2 = 56":
                return x1 * x1 * x1 + 23 * x1 - 56;
            case "x1^3 - x1 - x2 = -4":
                return x1 * x1 * x1 - x1 + 4;
        }
        return 0.0d;
    }

    double getFirstFunction(double x1, String s) {
        switch (s) {
            case "x1^2 + 25x1 - x2 = 0":
                return x1 * x1 + 25 * x1;
            case "x1^3 + 23x1 - x2 = 56":
                return x1 * x1 * x1 + 23 * x1 - 56;
            case "x1^3 - x1 - x2 = -4":
                return x1 * x1 * x1 - x2 + 4;
        }
        return 0.0d;
    }

    double getSecondFunction (double x1, double x2) {
        switch (secondFuncStr) {
            case "x1^2 + 25x1 - x2 = 0":
                return (x2 - x1 * x1) / 25;
            case "x1^3 + 23x1 - x2 = 56":
                return (56 - x1 * x1 * x1 + x2) / 23;
            case "x1^3 - x1 - x2 = -4":
                return -x2 + 4 - x1 * x1 *x1;
        }
        return 0.0d;
    }

    void setX1 (double x1) {this.x1 = x1;}
    void setX2 (double x2) {this.x2 = x2;}

    double getX1 () {return x1;}
    double getX2 () {return x2;}
}
