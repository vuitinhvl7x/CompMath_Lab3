package math;

import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class TrapezoidalRule {
    public static double calculateIntegral(double a, double b, Function f, double eps) throws NumberFormatException {
        double currentResult = 0, previousResult = 0;
        ArrayList<Double> secondDerivatives = new ArrayList<>();
        for (int n = 1; n <= 2 || Math.abs(currentResult - previousResult) > eps; n *= 2) {
            previousResult = currentResult;
            currentResult = 0;
            secondDerivatives.clear();
            double h = (b - a) / n;
            for (int i = 0; i <= n; i++) {
                double tmp = f.calculate(a + h * i);
                if (!Double.isFinite(tmp)) {
                    tmp = (f.calculate(a + h * i - eps) + f.calculate(a + h * i + eps)) / 2;
                }
                if (i == 0 || i == n) {
                    tmp /= 2;
                }
                currentResult += tmp;
                secondDerivatives.add(Math.abs(calculateSecondDerivative(a + h * i, f, eps)));
            }
            currentResult *= h;
            double R = secondDerivatives.stream().max(Double::compareTo).get() * Math.pow(b - a, 3) / (12 * Math.pow(n, 2));
            System.out.println("\u001B[34m" + "n = " + n + "\u001B[0m" + ", " + "\u001B[33m" + "integral = " + currentResult + "\u001B[0m" + ", " + "\u001B[31m" + "R = " + R + "\u001B[0m");
            if (R > Math.abs(currentResult)) {
                System.out.println("There is no answer because there is an irreparable gap on the chosen interval!:(");
                throw new NumberFormatException();
            }
        }
        return currentResult;
    }
    public static double calculateSecondDerivative(double x, Function f, double eps) {
        double f1 = f.calculate(x + 2 * eps);
        double f2 = f.calculate(x + eps);
        double f3 = f.calculate(x);
        return (f1 - 2 * f2 + f3) / (eps * eps);
    }
//    private static Double calcSecDerivative(double x, Function f, double eps) {
//        return (f.calculate(x + 2 * eps) - 2 * f.calculate(x + eps) + f.calculate(x)) / (eps * eps);
//    }


    public static class Transformer {
        public static String toPrecision(double n, double eps) {
            int count = 0;
            while (eps < 1) {
                eps *= 10;
                count++;
            }
            return String.format(Locale.ENGLISH, "%." + count + "f", n);
        }
    }

    public static class DataInterfaceUnit {
        private static final Scanner reader = new Scanner(System.in);

        private static void handleError(String msg) {
            System.out.println(msg);
            System.out.println("Enter = repeat input, any other key + Enter = exit:");
            if (reader.nextLine().isEmpty()) {
                return;
            }
            System.out.println("The program exits.");
            System.exit(-1);
        }

        public static double inputLowerLimit() {
            double a;
            while (true) {
                System.out.println("Enter the lower limit of the integral:");
                String tmp = reader.nextLine();
                try {
                    a = Double.parseDouble(tmp);
                    break;
                } catch (NumberFormatException e) {
                    handleError("Input Error! You need to enter a real number with a dot (\".\") as a separator.");
                }
            }
            return a;
        }

        public static double inputUpperLimit(double a) {
            double b;
            while (true) {
                System.out.println("Enter the upper limit of the integral");
                String tmp = reader.nextLine();
                try {
                    b = Double.parseDouble(tmp);
                    if (b <= a) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    handleError("Input error! You need to enter a valid number with a dot (\".\") as separator that is greater than \"" + a + "\".");
                }
            }
            return b;
        }

        public static Function inputFunction() {
            Function f;
            while (true) {
                System.out.println("Enter the integranl function f(x):");
                String tmp = reader.nextLine();
                f = new Function("f", tmp, "x");
                try {
                    if (!f.checkSyntax()) {
                        throw new IllegalArgumentException();
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    handleError("Input error! The function you entered did not pass the syntax check.");
                }
            }
            return f;
        }

        public static double inputEps() {
            double eps;
            while (true) {
                System.out.println("Enter the precision:");
                String tmp = reader.nextLine();
                try {
                    eps = Double.parseDouble(tmp);
                    if (eps <= 0 || eps >= 1) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    handleError("Input Error! You need to enter a positive real number that is less than 1, with a dot(\".\") as a separator.");
                }
            }
            return eps;
        }
    }
}
