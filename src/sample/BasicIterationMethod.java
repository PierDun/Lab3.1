package sample;

public class BasicIterationMethod extends ChordMethod {
    double previousX;
    double currentX;
    double lambda;

    @Override
    public String solve() {
        steps = 0;
        currentX = leftBorder;
        findLambda();

        if(isSolvable(leftBorder, rightBorder)) {
            while (Math.abs(currentX - previousX) > accuracy && lambda != 0) {
                previousX = currentX;
                currentX = previousX + lambda * function.solve(previousX, 0);
                steps++;
            }
            return String.format("Решение согласно методу простой итерации: %4.10f \n Количество итераций: %d", currentX, steps);
        } else return "уравнение не имеет корней \nили имеет более одного корня на данном интервале";
    }

    void findLambda() {
        switch (functionSir){
            case "x^2 + 25x = 0":
                lambda = -1 / (2 * rightBorder + 25);
                currentX = rightBorder;
                break;
            case "x^3+23x-56 = 0":
            case "x^3 - x + 4":
                if (leftBorder >= 0) {
                    lambda = -1 / getDerivativeFunction(rightBorder);
                    currentX = leftBorder;
                    break;
                }
                if (rightBorder <= 0) {
                    lambda = -1 / getDerivativeFunction(leftBorder);
                    currentX = rightBorder;
                    break;
                }
                if (leftBorder < 0 && rightBorder > 0) {
                    if (Math.abs(leftBorder) >= Math.abs(rightBorder)) {
                        lambda = -1 / getDerivativeFunction(leftBorder);
                        currentX = rightBorder;
                    }
                    else {
                        lambda = -1 / getDerivativeFunction(rightBorder);
                        currentX = leftBorder;
                    }
                    break;
                }
                break;
        }
    }
}
