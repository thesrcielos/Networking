package org.eci.sockets.ex3;

public class TrigonometricOperator {

    public enum Function {
        COS, SIN, TAN
    }

    private Function currentFunction;

    public TrigonometricOperator() {
        this.currentFunction = Function.COS;
    }

    public String processInput(String input) {
        input = input.trim().toLowerCase();

        if (input.startsWith("fun:")) {
            String newFunction = input.substring(4);
            switch (newFunction) {
                case "sin":
                    currentFunction = Function.SIN;
                    return "Function changed to sine.";
                case "cos":
                    currentFunction = Function.COS;
                    return "Function changed to cosine.";
                case "tan":
                    currentFunction = Function.TAN;
                    return "Function changed to tangent.";
                default:
                    return "Unrecognized function.";
            }
        } else {
            try {
                double number = parseExpression(input);
                double result;
                switch (currentFunction) {
                    case SIN:
                        result = Math.sin(number);
                        break;
                    case TAN:
                        result = Math.tan(number);
                        break;
                    case COS:
                    default:
                        result = Math.cos(number);
                        break;
                }
                return String.valueOf(result);
            } catch (Exception e) {
                return "Invalid input.";
            }
        }
    }

    // Parses expressions like "pi", "pi/2", "2*pi", "3*pi/4"
    private double parseExpression(String input) throws Exception {
        input = input.replace("π", "pi"); // support Unicode π
        input = input.replaceAll("\\s+", ""); // remove spaces

        if (input.equals("pi")) return Math.PI;

        // Replace pi with a variable
        input = input.replace("pi", String.valueOf(Math.PI));

        // Evaluate the expression (very basic and limited)
        return evaluateSimpleExpression(input);
    }

    // Very basic expression evaluator (supports *, /, and constants)
    private double evaluateSimpleExpression(String expr) throws Exception {
        // Handle multiplication and division
        String[] multiplyParts = expr.split("\\*");
        double result = 1.0;
        for (String part : multiplyParts) {
            if (part.contains("/")) {
                String[] divParts = part.split("/");
                double temp = Double.parseDouble(divParts[0]);
                for (int i = 1; i < divParts.length; i++) {
                    temp /= Double.parseDouble(divParts[i]);
                }
                result *= temp;
            } else {
                result *= Double.parseDouble(part);
            }
        }
        return result;
    }

}
