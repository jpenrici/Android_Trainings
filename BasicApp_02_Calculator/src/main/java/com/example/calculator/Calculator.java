package com.example.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator {

    protected static final String ERROR = "ERROR";
    protected static final String SPACE = " ";
    protected static final String EMPTY = "";

    protected static final char SEPARATOR = ',';
    protected static final char DELIMITER = ' ';

    protected static final String DIGITS = "0123456789";
    private static final String OPERATOR = "+-*/";
    private static final String SIGNAL = "+-";

    private static final char LPARENTHESES = '(';
    private static final char RPARENTHESES = ')';
    private static final char POSITIVE = '+';
    private static final char NEGATIVE = '-';
    private static final char DOT = '.';

    private static final String NUMBER = DIGITS + SEPARATOR;
    private static final String POSTFIX = NUMBER + OPERATOR;
    private static final String ALL = POSTFIX + LPARENTHESES + RPARENTHESES + DELIMITER;

    private String currentCharacters;
    private char currentDelimiter;

    public Calculator() {
        currentCharacters = ALL;
        currentDelimiter = DELIMITER;
    }

    public Calculator(char currentDelimiter) {
        setCurrentDelimiter(currentDelimiter);
    }

    public void setCurrentDelimiter(char currentDelimiter) {
        this.currentDelimiter = currentDelimiter;
        currentCharacters = POSTFIX + LPARENTHESES + RPARENTHESES + currentDelimiter;
    }

    public String calculate(String expression) {
        return resolve(postFix(expression));
    }

    protected String resolve(String postfixExpression) {

        if (isNullOrEmpty(postfixExpression)) {
            return ERROR;
        }

        if (!expressionIsValid(postfixExpression, true)) {
            return ERROR;
        }

        System.out.println(postfixExpression);

        List<String> tokens = new ArrayList<>();
        String[] array = postfixExpression.replace(currentDelimiter, ';').split(";");
        for (String token : array) {
            if (!token.replace(SPACE, EMPTY).isEmpty()) {
                tokens.add(token);
            }
        }

        if (tokens.size() == 0) {
            return ERROR;
        }

        Stack<Double> numbers = new Stack<>();
        for (String token : tokens) {
            if (OPERATOR.contains(token)) {
                if (numbers.isEmpty()) {
                    return ERROR;
                }
                Double operand2 = numbers.pop();
                if (numbers.isEmpty()) {
                    return ERROR;
                }
                Double operand1 = numbers.pop();
                switch (token) {
                    case "+":
                        numbers.push(operand1 + operand2);
                        break;
                    case "-":
                        numbers.push(operand1 - operand2);
                        break;
                    case "*":
                        numbers.push(operand1 * operand2);
                        break;
                    case "/":
                        if (operand2 != 0)
                            numbers.push(operand1 / operand2);
                        else
                            return ERROR;
                        break;
                }
            } else {
                try {
                    numbers.push(Double.parseDouble(token.replace(SEPARATOR, DOT)));
                } catch (Exception e) {
                    return ERROR;
                }
            }
        }

        if (numbers.size() != 1)
            return ERROR;

        String result = numbers.pop().toString().replace(DOT, SEPARATOR);
        array = result.replace(SEPARATOR, ';').split(";");
        if (array.length == 2) {
            if (Double.parseDouble(array[1]) == 0) {
                result = array[0] + ",0";
            }
        }

        return result;
    }

    private static int precedence(char key) {
        switch (key) {
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            case '(':
                return 1;
            default:
                return 0;
        }
    }

    protected String postFix(String expression) {

        if (isNullOrEmpty(expression)) {
            return ERROR;
        }

        expression = prepare(expression);
        if (!expressionIsValid(expression)) {
            return ERROR;
        }

        // Converter
        Stack<Character> stack = new Stack<>();
        StringBuilder postfixExpression = new StringBuilder(String.valueOf(currentDelimiter));

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == DELIMITER) {
                continue;
            }

            if (NUMBER.contains(String.valueOf(c))) {
                postfixExpression.append(c);
                continue;
            }

            if (postfixExpression.charAt(postfixExpression.length() - 1) != currentDelimiter) {
                postfixExpression.append(currentDelimiter);
            }

            if (c == LPARENTHESES) {
                stack.push(c);
            }
            if (c == RPARENTHESES) {
                while (stack.peek() != LPARENTHESES) {
                    if (postfixExpression.charAt(postfixExpression.length() - 1) != currentDelimiter)
                        postfixExpression.append(currentDelimiter);
                    postfixExpression.append(stack.pop().toString());
                }
                stack.pop();
            }

            if (OPERATOR.contains(String.valueOf(c))) {
                while (stack.size() > 0 && stack.peek() != LPARENTHESES &&
                        precedence(c) <= precedence(stack.peek())) {
                    if (postfixExpression.charAt(postfixExpression.length() - 1) != currentDelimiter)
                        postfixExpression.append(currentDelimiter);
                    postfixExpression.append(stack.pop().toString()).append(currentDelimiter);
                }
                stack.push(c);
            }
        }

        while (stack.size() > 0) {
            if (postfixExpression.charAt(postfixExpression.length() - 1) != currentDelimiter)
                postfixExpression.append(currentDelimiter);
            postfixExpression.append(stack.pop().toString());
        }

        if (postfixExpression.length() > 1) {
            if (postfixExpression.charAt(0) == currentDelimiter) {
                postfixExpression = new StringBuilder(postfixExpression.substring(1));
            }
        }

        return postfixExpression.toString();
    }

    protected String prepare(String expression) {

        if (isNullOrEmpty(expression)) {
            return EMPTY;
        }

        // Remover espaços
        expression = expression.replace(SPACE, EMPTY);

        // Tratar sinal no início
        if (expression.charAt(0) == POSITIVE || expression.charAt(0) == NEGATIVE) {
            expression = "0" + expression;
        }

        // Tratar números com sinais
        expression = expression.replace("--", "+");
        expression = expression.replace("+-", "-");
        expression = expression.replace("*+", "*");
        expression = expression.replace("/+", "/");
        expression = expression.replace("*-", "*(0-1)*");
        expression = expression.replace("/-", "*(0-1)/");
        expression = expression.replace("(-", "(0-");

        return expression;
    }

    protected boolean expressionIsValid(String expression, boolean isPostFix) {

        if (isNullOrEmpty(expression)) {
            return false;
        }

        String first = DIGITS + SIGNAL + LPARENTHESES;
        String last = DIGITS + RPARENTHESES;

        if (isPostFix) {
            first = DIGITS;
            last = DIGITS + OPERATOR;
        }

        if (!first.contains(String.valueOf(expression.charAt(0)))) {
            return false;
        }

        if (!last.contains(String.valueOf(expression.charAt(expression.length() - 1)))) {
            return false;
        }

        if (!isPostFix) {
            int digitCounter = 0;
            int leftParenthesisCounter = 0;
            int rightParenthesisCounter = 0;
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);
                if (!currentCharacters.contains(String.valueOf(c))) {
                    return false;
                }
                if (DIGITS.contains(String.valueOf(c))) {
                    digitCounter++;
                }
                if (c == LPARENTHESES) {
                    leftParenthesisCounter++;
                }
                if (c == RPARENTHESES) {
                    rightParenthesisCounter++;
                }
            }

            return leftParenthesisCounter == rightParenthesisCounter && digitCounter > 0;
        }

        return true;
    }

    protected boolean expressionIsValid(String expression) {
        return expressionIsValid(expression, false);
    }

    protected boolean isNumber(String value) {

        if (isNullOrEmpty(value)) {
            return false;
        }

        boolean isInteger = value.matches("[+-]?[0-9]+");
        boolean isFloat = value.matches("[+-]?[0-9]+" + SEPARATOR + "[0-9]+");

        return isInteger || isFloat;
    }

    protected boolean isNullOrEmpty(String expression) {
        return expression == null || expression.replace(SPACE, EMPTY).isEmpty();
    }

}
