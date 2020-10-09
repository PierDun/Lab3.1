package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {
    @FXML private CheckBox horde;
    @FXML private CheckBox iteration;

    @FXML private ChoiceBox<String> funChoose;
    @FXML private ChoiceBox<String> firstFun;
    @FXML private ChoiceBox<String> secondFun;

    @FXML private Label eqLabel;
    @FXML private TextField eqLeft;
    @FXML private TextField eqRight;
    @FXML private TextField eqAccuracy;

    @FXML private Label sysLabel;
    @FXML private TextField sysFirst;
    @FXML private TextField sysSecond;
    @FXML private TextField sysAcc;

    @FXML private LineChart<String, Double> eqChart;
    @FXML private LineChart<String, Double> sysChart;

    Functions currentFunction = (n,a)-> (n*n + 25*n);

    @FXML
    void initialize () {
        funChoose.getItems().addAll("x^2 + 25x = 0", "x^3+23x-56 = 0", "x^3 - x + 4");
        firstFun.getItems().addAll("x1^2 + 25x1 - x2 = 0", "x1^3 + 23x1 - x2 = 56", "x1^3 - x1 - x2 = -4");
        secondFun.getItems().addAll("x1^2 + 25x1 - x2 = 0", "x1^3 + 23x1 - x2 = 56", "x1^3 - x1 - x2 = -4");
    }

    void chooseEq () {
        switch (funChoose.getValue()){
            case "x^2 + 25x = 0":
                Main.setModeIndex(0);
                currentFunction = (n, a)-> (n*n + 25*n);
                break;
            case "x^3+23x-56 = 0":
                Main.setModeIndex(1);
                currentFunction = (n,a)-> (n*n*n + 23*n - 56);
                break;
            case "x^3 - x + 4":
                Main.setModeIndex(3);
                currentFunction = (n,a)-> (n*n*n - n + 4);
                break;
        }
    }

    @FXML
    public void solveEquation (ActionEvent actionEvent) {
        double left = Double.parseDouble(eqLeft.getText());
        double right = Double.parseDouble(eqRight.getText());
        double acc = Double.parseDouble(eqAccuracy.getText());

        String answer = "";

        chooseEq();
        ChordMethod chordMethod = new ChordMethod();
        BasicIterationMethod iterationMethod = new BasicIterationMethod();

        if (horde.isSelected()) {
            chordMethod.setLeftBorder(left);
            chordMethod.setRightBorder(right);
            chordMethod.setAccuracy(acc);
            chordMethod.setFunction(currentFunction);
            chordMethod.setFunctionSir(funChoose.getValue());

            if (acc != 0) answer = answer + chordMethod.solve() + "\n";
            else eqLabel.setText("Погрешность недолжна быть нулевой");
        }

        if (iteration.isSelected()) {
            iterationMethod.setLeftBorder(left);
            iterationMethod.setRightBorder(right);
            iterationMethod.setAccuracy(acc);
            iterationMethod.setFunction(currentFunction);
            iterationMethod.setFunctionSir(funChoose.getValue());

            if (acc != 0) answer= answer + iterationMethod.solve() + "\n";
            else eqLabel.setText("Погрешность недолжна быть нулевой");
        }

        eqLabel.setText(answer);

        double step = (right - left) / 50;
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (double x = Double.parseDouble(eqLeft.getText()); x < Double.parseDouble(eqRight.getText()); x = x + step) {
            if (Math.abs(x - chordMethod.currentX) <= step / 2 && horde.isSelected()) {
                XYChart.Data<String, Double> data = new XYChart.Data<>(String.format("%4.3f", x), currentFunction.solve(x,0));
                data.setNode(new Circle(3, Color.YELLOW));
                series.getData().add(data);
            } else if (Math.abs(x - iterationMethod.currentX) <= step / 2 && iteration.isSelected()) {
                XYChart.Data<String, Double> data = new XYChart.Data<>(String.format("%4.3f", x), currentFunction.solve(x,0));
                data.setNode(new Circle(3, Color.BLUE));
                series.getData().add(data);
            }
            else series.getData().add(new XYChart.Data<>(String.format("%4.2f", x), currentFunction.solve(x,0)));
        }

        eqChart.getData().setAll(series);

    }

    @FXML
    public void solveSystem (javafx.event.ActionEvent actionEvent) {
        double first = Double.parseDouble(sysFirst.getText());
        double second = Double.parseDouble(sysSecond.getText());
        double acc = Double.parseDouble(sysAcc.getText());

        if (!firstFun.getValue().equals(secondFun.getValue())) {
            IterationMethodForSystem methodForSystem = new IterationMethodForSystem();
            methodForSystem.acc = acc;
            methodForSystem.setX1(first);
            methodForSystem.setX2(second);

            if (firstFun.getValue().equals("x1^3 + 23x1 - x2 = 56") || (firstFun.getValue().equals("x1^2 + 25x1 - x2 = 0") && secondFun.getValue().equals("x1^3 - x1 - x2 = -4"))) {
                methodForSystem.firstFuncStr = secondFun.getValue();
                methodForSystem.secondFuncStr = firstFun.getValue();
            } else {
                methodForSystem.firstFuncStr = firstFun.getValue();
                methodForSystem.secondFuncStr = secondFun.getValue();
            }

            sysLabel.setText(methodForSystem.solve());

            double step = 0.05;
            XYChart.Series<String, Double> series1 = new XYChart.Series<>();
            XYChart.Series<String, Double> series2 = new XYChart.Series<>();

            for (double x = methodForSystem.getX1() - 2; x < methodForSystem.getX1() + 2; x = x + step) {
                if (Math.abs(x - methodForSystem.getX1()) <= step / 2) {
                    XYChart.Data<String, Double> data = new XYChart.Data<>(String.format("%4.3f", x), methodForSystem.getX2());
                    data.setNode(new Circle(3, Color.BLACK));
                    series1.getData().add(data);
                } else {
                    series2.getData().add(new XYChart.Data<>(String.format("%4.2f", x), methodForSystem.getFirstFunction(x, methodForSystem.secondFuncStr)));
                    series1.getData().add(new XYChart.Data<>(String.format("%4.2f", x), methodForSystem.getFirstFunction(x)));
                }
            }

            series1.setName(firstFun.getValue());
            series2.setName(secondFun.getValue());

            sysChart.getData().setAll(series1, series2);

        } else sysLabel.setText("Выбрана одна и таже функция дважды");
    }
}
