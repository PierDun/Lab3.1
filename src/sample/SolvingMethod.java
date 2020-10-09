package sample;

public abstract class SolvingMethod {
    double accuracy;
    double leftBorder;
    double rightBorder;
    Functions function = null;
    double currentX;
    int steps = 0;
    String functionSir;

    public  abstract String solve();

    public double getDerivativeFunction(double x) {
        switch (functionSir){
            case "x^2 + 25x = 0":
                return 2 * x + 25;
            case "x^3+23x-56 = 0":
                return 3 * x * x+23;
            case "x^3 - x + 4":
                return 3 * x * x - 1;
        }
        return  0;
    }

    public boolean isSolvable (double a, double b){
        return !(function.solve(a, 0) * function.solve(b, 0) > 0);
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(double leftBorder) {
        this.leftBorder = leftBorder;
    }

    public double getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(double rightBorder) {
        this.rightBorder = rightBorder;
    }

    public Functions getFunction() {
        return function;
    }

    public void setFunction(Functions function) {
        this.function = function;
    }

    public void setFunctionSir(String functionSir) {
        this.functionSir = functionSir;
    }

}
