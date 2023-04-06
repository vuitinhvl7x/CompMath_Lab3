import math.TrapezoidalRule;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.License;

import static math.TrapezoidalRule.Transformer.toPrecision;
import static math.TrapezoidalRule.DataInterfaceUnit.*;
/* Non-Commercial Use Confirmation */

public class Main {
    public static void main(String[] args) {

        /* Verification if use type has been already confirmed */
        boolean isCallSuccessful = License.iConfirmNonCommercialUse("Xuan Hai");
        boolean isConfirmed = License.checkIfUseTypeConfirmed();

        System.out.println("-------------The Trapezoid method-------------");
        double a = inputLowerLimit();
        double b = inputUpperLimit(a);
        Function f = inputFunction();
        double eps = inputEps();
        try {
            System.out.println("\u001B[32m" + "Answer: " + toPrecision(TrapezoidalRule.calculateIntegral(a, b, f, eps), eps));
        } catch (NumberFormatException e) {
        }
    }
}
