package com.example.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void isNullOrEmpty() {
        assertTrue(calculator.isNullOrEmpty(""));
        assertTrue(calculator.isNullOrEmpty(" "));
        assertTrue(calculator.isNullOrEmpty(null));
        assertFalse(calculator.isNullOrEmpty("0"));
    }

    @Test
    public void numbers() {
        // Não numéricos
        assertFalse(calculator.isNumber(""));
        assertFalse(calculator.isNumber(" "));
        assertFalse(calculator.isNumber(null));
        assertFalse(calculator.isNumber("A"));
        assertFalse(calculator.isNumber("-"));
        assertFalse(calculator.isNumber("+"));
        assertFalse(calculator.isNumber("*"));
        assertFalse(calculator.isNumber("/"));
        assertFalse(calculator.isNumber("("));
        assertFalse(calculator.isNumber(")"));
        assertFalse(calculator.isNumber(","));
        assertFalse(calculator.isNumber("."));
        assertFalse(calculator.isNumber("-,"));
        assertFalse(calculator.isNumber("(10+10.1)"));     // expressão
        // Numéricos com erros
        assertFalse(calculator.isNumber("0.1"));           // separador decimal != ','
        assertFalse(calculator.isNumber(".1"));
        assertFalse(calculator.isNumber("1."));
        assertFalse(calculator.isNumber(",1"));            // erro digitação
        assertFalse(calculator.isNumber("1,"));
        assertFalse(calculator.isNumber("-1,"));
        assertFalse(calculator.isNumber("-,1"));
        assertFalse(calculator.isNumber("*1"));
        assertFalse(calculator.isNumber("/1"));
        assertFalse(calculator.isNumber("(1"));
        assertFalse(calculator.isNumber("1)"));
        assertFalse(calculator.isNumber("0,,1"));
        assertFalse(calculator.isNumber("-1,1,"));
        assertFalse(calculator.isNumber("+1,1,"));
        assertFalse(calculator.isNumber("0,0,1"));
        assertFalse(calculator.isNumber(",,1"));
        assertFalse(calculator.isNumber("1 "));
        assertFalse(calculator.isNumber(" 1"));
        assertFalse(calculator.isNumber("1A"));
        assertFalse(calculator.isNumber("0,A"));
        // String numéricas
        assertTrue(calculator.isNumber("0"));
        assertTrue(calculator.isNumber("1"));
        assertTrue(calculator.isNumber("-1"));
        assertTrue(calculator.isNumber("+1"));
        assertTrue(calculator.isNumber("10"));
        assertTrue(calculator.isNumber("0,1"));
        assertTrue(calculator.isNumber("-1,1"));
        assertTrue(calculator.isNumber("+1,1"));
    }

    @Test
    public void validate() {
        // Nulo ou vazio
        assertFalse(calculator.expressionIsValid(""));
        assertFalse(calculator.expressionIsValid(" "));
        assertFalse(calculator.expressionIsValid(null));
        // Caracteres inválidos
        assertFalse(calculator.expressionIsValid("a"));
        assertFalse(calculator.expressionIsValid("(0,1+-,2/5.)"));  // separador decimal != ','
        assertFalse(calculator.expressionIsValid("(01,23+-,4*5,6/7-8+9,)+A"));
        // Parênteses sem par
        assertFalse(calculator.expressionIsValid("(((1+2))"));      // '(' > ')'
        assertFalse(calculator.expressionIsValid("(((01,23+-,4*5,6/7-8+9,))))"));
        // Somente caracteres válidos
        assertFalse(calculator.expressionIsValid("-"));
        assertFalse(calculator.expressionIsValid("*"));
        assertFalse(calculator.expressionIsValid("/"));
        assertFalse(calculator.expressionIsValid("+"));
        assertFalse(calculator.expressionIsValid("("));
        assertFalse(calculator.expressionIsValid(")"));
        assertFalse(calculator.expressionIsValid("()"));
        assertFalse(calculator.expressionIsValid("( )"));
        assertFalse(calculator.expressionIsValid("*1"));
        assertFalse(calculator.expressionIsValid("/1"));
        assertFalse(calculator.expressionIsValid(")1"));
        assertFalse(calculator.expressionIsValid("(1"));
        assertFalse(calculator.expressionIsValid("1 )"));
        assertFalse(calculator.expressionIsValid("* 1"));
        assertFalse(calculator.expressionIsValid("( 1 - "));
        assertFalse(calculator.expressionIsValid(" 0"));    // não permitir iniciar com espaço
        assertTrue(calculator.expressionIsValid("0"));
        assertTrue(calculator.expressionIsValid("-1"));
        assertTrue(calculator.expressionIsValid("- 1"));    // permitir se todos caracteres válidos
        assertTrue(calculator.expressionIsValid("0+1"));
        assertTrue(calculator.expressionIsValid("- 0 +1"));
        assertTrue(calculator.expressionIsValid("+(-1)"));
        assertTrue(calculator.expressionIsValid("+ ( - 1 )"));
    }

    @Test
    public void prepare() {
        // Soma de número negativo '+-'
        assertEquals("1-1", calculator.prepare("1+-1"));
        // Operador '+' junto ao '-'
        assertEquals("0-1", calculator.prepare("+-1"));
        // Operador '+' no início
        assertEquals("0+1-2", calculator.prepare("+1+-2"));
        // Operador '-' no início
        assertEquals("0-1-1", calculator.prepare("-1-1"));
        // Multiplicação de número negativo '*-'
        assertEquals("1*(0-1)*1", calculator.prepare("1*-1"));
        // Multiplicação de número positivo '*+'
        assertEquals("1*1", calculator.prepare("1*+1"));
        // Divisão de número negativo '*-'
        assertEquals("1*(0-1)/1", calculator.prepare("1/-1"));
        // Divisão de número negativo '*+'
        assertEquals("1/1", calculator.prepare("1/+1"));
        // Operador '-' próximo a parênteses
        assertEquals("0-(0-(0-15*(0-1)*10)))", calculator.prepare("-(-(-15*-10)))"));
    }

    @Test
    public void postFix() {
        //Inválido
        assertEquals("ERROR", calculator.postFix(""));
        assertEquals("ERROR", calculator.postFix(" "));
        assertEquals("ERROR", calculator.postFix(null));
        assertEquals("ERROR", calculator.postFix("expression"));
        //Número
        assertEquals("0", calculator.postFix("0"));
        assertEquals("0 1 -", calculator.postFix("-1"));
        assertEquals("0 0,1 -", calculator.postFix("-0,1"));       //valor fracionado
        //Expressão
        assertEquals("0 1 - 1 +", calculator.postFix("- 1 + 1"));  //espaço
        assertEquals("0 1 - 1 +", calculator.postFix("-1+1"));
        assertEquals("0 1 + 2 -", calculator.postFix("0+1-2"));
        assertEquals("1 2 + 3 +", calculator.postFix("1+2+3"));
        assertEquals("1,1 1,1 +", calculator.postFix("1,1+1,1"));
        assertEquals("1 1 1 + +", calculator.postFix("1+(1+1)"));
        assertEquals("1 0,1 + 2 +", calculator.postFix("(1+0,1)+2"));
        assertEquals("1 0,1 * 15 -", calculator.postFix("(1*0,1)-15"));
        assertEquals("1 0,1 / 1 -", calculator.postFix("(1/0,1)+-1"));
        assertEquals("1 0,1 / 0 1 - * 1 *", calculator.postFix("(1/0,1)*-1"));
        assertEquals("1 0,1 / 0 1 - * 1 * 15 +", calculator.postFix("(1/0,1)*-1+15"));
        assertEquals("1 0,1 / 0 1 - * 5 /", calculator.postFix("(1/0,1)/-5"));
        assertEquals("1 0,1 / 0 1 - 1 + /", calculator.postFix("(1/0,1)/(-1+1)"));
        assertEquals("1 1,1 - 0 5 - 1 + -", calculator.postFix("((1+-1,1))+-((-5+1))"));
        assertEquals("0 0 1 - - 0 2 - 3 + -", calculator.postFix("-(((-1)))+-(((-2+3)))"));
        assertEquals("0 0 1 - + 0 2 - 3 + -", calculator.postFix("+(((-1)))+-(((-2+3)))"));
        assertEquals("1,0 4,0 +", calculator.postFix("1,0+4,0"));
        assertEquals("1,0 4,0 + 2,0 + 3 +", calculator.postFix("1,0+4,0+2,0+3"));
        assertEquals("5,0 1,0 -", calculator.postFix("5,0-1,0"));
        assertEquals("5,0 2,0 - 2 -", calculator.postFix("5,0-2,0-2"));
        assertEquals("5,0 2,0 *", calculator.postFix("5,0*2,0"));
        assertEquals("5,0 2,0 * 2 *", calculator.postFix("5,0*2,0*2"));
        assertEquals("10,0 2,0 /", calculator.postFix("10,0/2,0"));
        assertEquals("10,0 2,0 / 2 / 10 /", calculator.postFix("10,0/2,0/2/10"));
        assertEquals("10 10 + 5 2 * -", calculator.postFix("(10+10)-(5*2)"));
        assertEquals("1,5 2,5 + 3 + 2,5 2,5 + -", calculator.postFix("(1,5+2,5+3)-(2,5+2,5)"));
        assertEquals("5 0 1 - * 10 * 20 + 5 -", calculator.postFix("5*-10+20-5"));
        assertEquals("2000 1 2 / +", calculator.postFix("2000+1/2"));
        assertEquals("2 2 + 4 5 * + 1 1000 / +", calculator.postFix("2+2+4*5+1/1000"));
    }

    @Test
    public void resolve() {
        // Inválido
        assertEquals("ERROR", calculator.resolve(""));
        assertEquals("ERROR", calculator.resolve(" "));
        assertEquals("ERROR", calculator.resolve(null));
        assertEquals("ERROR", calculator.resolve("A"));
        assertEquals("ERROR", calculator.resolve(",1"));
        assertEquals("ERROR", calculator.resolve(" 0"));       // não permitir iniciar com espaço
        assertEquals("ERROR", calculator.resolve("(1 + 2)"));  // input: infix
        // Número
        assertEquals("0,0", calculator.resolve("0"));
        assertEquals("1,1", calculator.resolve("1,1"));
        // Expressão
        assertEquals("ERROR", calculator.resolve("1 0,1 / 0 1 - 1 + /"));  // Divisão por zero
        assertEquals("0,25", calculator.resolve("10,0 2,0 / 2 / 10 /"));
        assertEquals("10,0", calculator.resolve("10 10 + 5 2 * -"));
        assertEquals("2,0", calculator.resolve("1,5 2,5 + 3 + 2,5 2,5 + -"));
        assertEquals("-1,0", calculator.resolve("0 1 -"));
        assertEquals("0,0", calculator.resolve("0 1 - 1 +"));
        assertEquals("-1,0", calculator.resolve("0 1 + 2 -"));
        assertEquals("6,0", calculator.resolve("1 2 + 3 +"));
        assertEquals("2,2", calculator.resolve("1,1 1,1 +"));
        assertEquals("3,1", calculator.resolve("1 0,1 + 2 +"));
        assertEquals("-14,9", calculator.resolve("1 0,1 * 15 -"));
        assertEquals("9,0", calculator.resolve("1 0,1 / 1 -"));
        assertEquals("-10,0", calculator.resolve("1 0,1 / 0 1 - * 1 *"));
        assertEquals("5,0", calculator.resolve("1 0,1 / 0 1 - * 1 * 15 +"));
        assertEquals("-2,0", calculator.resolve("1 0,1 / 0 1 - * 5 /"));
        assertEquals("3,9", calculator.resolve("1 1,1 - 0 5 - 1 + -"));
        assertEquals("0,0", calculator.resolve("0 0 1 - - 0 2 - 3 + -"));
        assertEquals("-2,0", calculator.resolve("0 0 1 - + 0 2 - 3 + -"));
        assertEquals("-35,0", calculator.resolve("5 0 1 - * 10 * 20 + 5 -"));
        assertEquals("2000,5", calculator.resolve("2000 1 2 / +"));
        assertEquals("24,001", calculator.resolve("2 2 + 4 5 * + 1 1000 / +"));
        assertEquals("2,0", calculator.resolve("1,5 2,5 + 3 + 2,5 2,5 + -"));
        assertEquals("757,0", calculator.resolve("100 200 + 2 / 5 * 7 +"));
        assertEquals("-4,0", calculator.resolve("2 3 1 * + 9 -"));
        assertEquals("23,0", calculator.resolve("10 2 8 * + 3 -"));
    }

    @Test
    public void calc() {
        assertEquals("ERROR", calculator.calculate(""));
        assertEquals("ERROR", calculator.calculate(" "));
        assertEquals("ERROR", calculator.calculate("A"));
        assertEquals("ERROR", calculator.calculate("+"));
        assertEquals("ERROR", calculator.calculate("- "));
        assertEquals("ERROR", calculator.calculate("( "));
        assertEquals("ERROR", calculator.calculate("()"));
        assertEquals("ERROR", calculator.calculate(" *1"));
        assertEquals("ERROR", calculator.calculate("/ 1"));
        assertEquals("ERROR", calculator.calculate("1-"));
        assertEquals("ERROR", calculator.calculate(" 1 - "));
        assertEquals("ERROR", calculator.calculate(" 2 / * 3"));
        assertEquals("ERROR", calculator.calculate(" 2 / * + 3"));
        assertEquals("ERROR", calculator.calculate("(1/0,1)/(-1+1)"));
        assertEquals("1,0", calculator.calculate(" 1"));
        assertEquals("1,0", calculator.calculate(" +1"));
        assertEquals("0,0", calculator.calculate("0"));
        assertEquals("1,0", calculator.calculate("1"));
        assertEquals("-1,0", calculator.calculate("-1"));
        assertEquals("0,0", calculator.calculate("-1+1"));
        assertEquals("-1,0", calculator.calculate("0+1-2"));
        assertEquals("6,0", calculator.calculate("1+2+3"));
        assertEquals("2,2", calculator.calculate("1,1+1,1"));
        assertEquals("3,1", calculator.calculate("(1+0,1)+2"));
        assertEquals("-14,9", calculator.calculate("(1*0,1)-15"));
        assertEquals("9,0", calculator.calculate("(1/0,1)+-1"));
        assertEquals("-10,0", calculator.calculate("(1/0,1)*-1"));
        assertEquals("-10,0", calculator.calculate("(1/0,1)/-1"));
        assertEquals("3,9", calculator.calculate("((1+-1,1))+-((-5+1))"));
        assertEquals("0,0", calculator.calculate("-(((-1)))+-(((-2+3)))"));
        assertEquals("-35,0", calculator.calculate("5*-10+20-5"));
        assertEquals("2000,5", calculator.calculate("2000+1/2"));
        assertEquals("24,001", calculator.calculate("2+2+4*5+1/1000"));
        assertEquals("23,0", calculator.calculate("((10 + (2 * 8)) - 3)"));
        assertEquals("0,6666666666666666", calculator.calculate("2 / 3"));
    }

    @Test
    public void extra() {
        // Outro delimitador
        Calculator t1 = new Calculator('|');
        String value = t1.postFix("1+2*5,0+-1");
        assertEquals("1|2|5,0|*|+|1|-", value);
        String result = t1.resolve(value);
        assertEquals("10,0", result);
    }

}