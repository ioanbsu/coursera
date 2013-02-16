package com.artigile.coursera.algorythms.week2;

import com.artigile.coursera.algorythms.week2.stackandqueue.ResizingArrayStack;
import com.artigile.coursera.algorythms.week2.stackandqueue.Stack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author IoaN, 2/15/13 8:59 PM
 */
public class Dijkstra {

    Pattern numberPattern = Pattern.compile("^\\d+");
    Pattern operationPattern = Pattern.compile("^[-+*/]");

    public Double calculateExpressionValue(String expression) {
        Stack<String> operationsStack = new ResizingArrayStack<String>();
        Stack<Double> valuesStack = new ResizingArrayStack<Double>();
        while (!expression.isEmpty()) {
            expression = expression.trim();
            if (expression.startsWith("(")) {
                expression = expression.substring(1);
            }
            Matcher numberAtTheBeginningMatcher = numberPattern.matcher(expression);
            if (numberAtTheBeginningMatcher.find()) {
                valuesStack.push(Double.valueOf(numberAtTheBeginningMatcher.group(0)));
                expression = numberAtTheBeginningMatcher.replaceFirst("");
            }
            expression = expression.trim();

            Matcher operationMatcher = operationPattern.matcher(expression);
            if (operationMatcher.find()) {
                operationsStack.push(operationMatcher.group(0));
                expression = operationMatcher.replaceFirst("");
            }
            expression = expression.trim();

            if (expression.startsWith(")")) {
                String operation = operationsStack.pop();
                performOperation(valuesStack, operation);
                expression = expression.replaceFirst("\\)", "");
            }
            expression = expression.trim();
        }
        while (!operationsStack.isEmpty()) {
            performOperation(valuesStack, operationsStack.pop());
        }

        return valuesStack.pop();
    }

    private void performOperation(Stack<Double> valuesStack, String operation) {
        if ("+".equals(operation)) {
            valuesStack.push(valuesStack.pop() + valuesStack.pop());
        } else if ("-".equals(operation)) {
            valuesStack.push(-1 * (valuesStack.pop() - valuesStack.pop()));
        } else if ("*".equals(operation)) {
            valuesStack.push(valuesStack.pop() * valuesStack.pop());
        } else if ("/".equals(operation)) {
            Double value1 = valuesStack.pop();
            Double value2 = valuesStack.pop();
            valuesStack.push(value2 / value1);
        }
    }
}
