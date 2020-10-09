package sample;

public class ChordMethod extends SolvingMethod {
    double previousX;

    @Override
    public String solve() {

        if(isSolvable(leftBorder, rightBorder)) {
            currentX = leftBorder;

            while (Math.abs(currentX - previousX) > accuracy) {
                previousX = currentX;
                currentX = (leftBorder * function.solve(rightBorder,0) - rightBorder * function.solve(leftBorder,0))/
                        (function.solve(rightBorder,0) - function.solve(leftBorder,0));
                if (function.solve(leftBorder,0) * function.solve(currentX,0) > 0) leftBorder = currentX;
                else rightBorder = currentX;
                steps++;
            }
            return String.format("Решение согласно методу хорд: %4.10f \n Количество итераций: %d", currentX, steps);
        }else return "уравнение не имеет корней \nили имеет более одного корня на данном интервале";
    }
}
