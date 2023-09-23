#include "Calculator.hpp"

#include <cassert>
#include <chrono>
#include <iostream>
#include <memory>

void calc();
void extra();
void isEmpty();
void numbers();
void postFix();
void prepare();
void resolve();
void text();
void validate();

auto main() -> int
{
    std::cout << "Start tests ...\n";
    const auto start = std::chrono::steady_clock::now();

    // Sequence
    text();
    isEmpty();
    numbers();
    validate();
    prepare();
    postFix();
    resolve();
    calc();
    extra();

    const auto end = std::chrono::steady_clock::now();
    auto milliseconds = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();

    std::cout << "Finished tests.\nElapsed: " << milliseconds << " miliseconds\n";

    return 0;
}

void text()
{
    Calculator calculator;

    assert(calculator.replace(" ", ' ', '\0') == "");
    assert(calculator.replace("     ", ' ', '\0') == "");
    assert(calculator.replace(" ", " ", "") == "");
    assert(calculator.replace("     ", " ", "") == "");
    assert(calculator.replace("abcde", "b", "123B") == "a123Bcde");
    assert(calculator.replace("abcde", "abc", "CBA") == "CBAde");
    assert(calculator.replace("10+10=4*5=20", "4*5", "2*10") == "10+10=2*10=20");
    assert(calculator.contains("+-*/", "/") == true);
    assert(calculator.contains("+-*/", '/') == true);
    assert(calculator.contains("+1,01",  '+') == true);
    assert(calculator.contains("+1,01", ",0") == true);
    assert(calculator.contains("+1,01",  '-') == false);
    assert(calculator.contains("+1,01", ",1") == false);
    assert(calculator.rtrimZeros("0")  ==  "");
    assert(calculator.rtrimZeros(",0") == ",");
    assert(calculator.rtrimZeros("010") == "01");
    assert(calculator.rtrimZeros("1,0") == "1,");
    assert(calculator.rtrimZeros("+10") == "+1");
    assert(calculator.rtrimZeros("+100") == "+1");
    assert(calculator.rtrimZeros("-10,010") == "-10,01");

    std::cout << "Text test finished!\n";
}

void isEmpty()
{
    Calculator calculator;

    assert(calculator.isEmpty("")  == true);
    assert(calculator.isEmpty(" ") == true);
    assert(calculator.isEmpty("0") == false);

    std::cout << "Is Empty test finished!\n";
}

void numbers()
{
    Calculator calculator;

    // Não numéricos
    assert(calculator.isNumber("")  == false);
    assert(calculator.isNumber(" ") == false);
    assert(calculator.isNumber("A") == false);
    assert(calculator.isNumber("-") == false);
    assert(calculator.isNumber("+") == false);
    assert(calculator.isNumber("*") == false);
    assert(calculator.isNumber("/") == false);
    assert(calculator.isNumber("(") == false);
    assert(calculator.isNumber(")") == false);
    assert(calculator.isNumber(",") == false);
    assert(calculator.isNumber(".") == false);
    assert(calculator.isNumber("-,")  == false);
    assert(calculator.isNumber("0-1") == false);           // expressão
    assert(calculator.isNumber("(10+10.1)") == false);     // expressão
    // Numéricos com erros
    assert(calculator.isNumber("0.1") == false);           // separador decimal != ','
    assert(calculator.isNumber(".1")  == false);
    assert(calculator.isNumber("1.")  == false);
    assert(calculator.isNumber(",1")  == false);           // erro digitação
    assert(calculator.isNumber("1,")  == false);
    assert(calculator.isNumber("-1,") == false);
    assert(calculator.isNumber("-,1") == false);
    assert(calculator.isNumber("*1")  == false);
    assert(calculator.isNumber("/1")  == false);
    assert(calculator.isNumber("(1")  == false);
    assert(calculator.isNumber("1)")  == false);
    assert(calculator.isNumber("0,,1")  == false);
    assert(calculator.isNumber("-1,1,") == false);
    assert(calculator.isNumber("+1,1,") == false);
    assert(calculator.isNumber("0,0,1") == false);
    assert(calculator.isNumber(",,1") == false);
    assert(calculator.isNumber("1 ")  == false);
    assert(calculator.isNumber(" 1")  == false);
    assert(calculator.isNumber("1A")  == false);
    assert(calculator.isNumber("0,A") == false);
    // String numéricas
    assert(calculator.isNumber("0") == true);
    assert(calculator.isNumber("1") == true);
    assert(calculator.isNumber("-1") == true);
    assert(calculator.isNumber("+1") == true);
    assert(calculator.isNumber("10") == true);
    assert(calculator.isNumber("0,1")  == true);
    assert(calculator.isNumber("-1,1") == true);
    assert(calculator.isNumber("+1,1") == true);

    std::cout << "Numbers test finished!\n";
}

void validate()
{
    Calculator calculator;

    // Nulo ou vazio
    assert(calculator.expressionIsValid("")  == false);
    assert(calculator.expressionIsValid(" ") == false);
    // Caracteres inválidos
    assert(calculator.expressionIsValid("a") == false);
    assert(calculator.expressionIsValid("(0,1+-,2/5.)") == false);  // separador decimal != ','
    assert(calculator.expressionIsValid("(01,23+-,4*5,6/7-8+9,)+A") == false);
    // Parênteses sem par
    assert(calculator.expressionIsValid("(((1+2))") == false);      // '(' > ')'
    assert(calculator.expressionIsValid("(((01,23+-,4*5,6/7-8+9,))))") == false);
    // Somente caracteres válidos
    assert(calculator.expressionIsValid("-") == false);
    assert(calculator.expressionIsValid("*") == false);
    assert(calculator.expressionIsValid("/") == false);
    assert(calculator.expressionIsValid("+") == false);
    assert(calculator.expressionIsValid("(") == false);
    assert(calculator.expressionIsValid(")") == false);
    assert(calculator.expressionIsValid("()")  == false);
    assert(calculator.expressionIsValid("( )") == false);
    assert(calculator.expressionIsValid("*1") == false);
    assert(calculator.expressionIsValid("/1") == false);
    assert(calculator.expressionIsValid(")1") == false);
    assert(calculator.expressionIsValid("(1") == false);
    assert(calculator.expressionIsValid("1 )") == false);
    assert(calculator.expressionIsValid("* 1") == false);
    assert(calculator.expressionIsValid("( 1 - ") == false);
    assert(calculator.expressionIsValid(" 0") == false);    // não permitir iniciar com espaço
    assert(calculator.expressionIsValid("0")  == true);
    assert(calculator.expressionIsValid("-1") == true);
    assert(calculator.expressionIsValid("- 1") == true);    // permitir se todos caracteres válidos
    assert(calculator.expressionIsValid("0+1") == true);
    assert(calculator.expressionIsValid("- 0 +1") == true);
    assert(calculator.expressionIsValid("+(-1)")  == true);
    assert(calculator.expressionIsValid("+ ( - 1 )") == true);

    std::cout << "Validate test finished!\n";
}

void prepare()
{
    Calculator calculator;

    // Soma de número negativo '+-'
    assert("1-1" == calculator.prepare("1+-1"));
    // Operador '+' junto ao '-'
    assert("0-1" == calculator.prepare("+-1"));
    // Operador '+' no início
    assert("0+1-2" == calculator.prepare("+1+-2"));
    // Operador '-' no início
    assert("0-1-1" == calculator.prepare("-1-1"));
    // Multiplicação de número negativo '*-'
    assert("1*(0-1)*1" == calculator.prepare("1*-1"));
    // Multiplicação de número positivo '*+'
    assert("1*1" == calculator.prepare("1*+1"));
    // Divisão de número negativo '*-'
    assert("1*(0-1)/1" == calculator.prepare("1/-1"));
    // Divisão de número negativo '*+'
    assert("1/1" == calculator.prepare("1/+1"));
    // Operador '-' próximo a parênteses
    assert("0-(0-(0-15*(0-1)*10)))" == calculator.prepare("-(-(-15*-10)))"));

    std::cout << "Prepare test finished!\n";
}

void postFix()
{
    Calculator calculator;

    //Inválido
    assert("ERROR" == calculator.postFix(""));
    assert("ERROR" == calculator.postFix(" "));
    assert("ERROR" == calculator.postFix("expression"));
    //Número
    assert("0"       == calculator.postFix("0"));
    assert("0 1 -"   == calculator.postFix("-1"));
    assert("0 0,1 -" == calculator.postFix("-0,1"));       //valor fracionado
    //Expressão
    assert("0 1 - 1 +" == calculator.postFix("- 1 + 1"));  //espaço
    assert("0 1 - 1 +" == calculator.postFix("-1+1"));
    assert("0 1 + 2 -" == calculator.postFix("0+1-2"));
    assert("1 2 + 3 +" == calculator.postFix("1+2+3"));
    assert("1,1 1,1 +" == calculator.postFix("1,1+1,1"));
    assert("1 1 1 + +" == calculator.postFix("1+(1+1)"));
    assert("1 0,1 + 2 +"  == calculator.postFix("(1+0,1)+2"));
    assert("1 0,1 * 15 -" == calculator.postFix("(1*0,1)-15"));
    assert("1 0,1 / 1 -"  == calculator.postFix("(1/0,1)+-1"));
    assert("1 0,1 / 0 1 - * 1 *" == calculator.postFix("(1/0,1)*-1"));
    assert("1 0,1 / 0 1 - * 1 * 15 +" == calculator.postFix("(1/0,1)*-1+15"));
    assert("1 0,1 / 0 1 - * 5 /" == calculator.postFix("(1/0,1)/-5"));
    assert("1 0,1 / 0 1 - 1 + /" == calculator.postFix("(1/0,1)/(-1+1)"));
    assert("1 1,1 - 0 5 - 1 + -" == calculator.postFix("((1+-1,1))+-((-5+1))"));
    assert("0 0 1 - - 0 2 - 3 + -" == calculator.postFix("-(((-1)))+-(((-2+3)))"));
    assert("0 0 1 - + 0 2 - 3 + -" == calculator.postFix("+(((-1)))+-(((-2+3)))"));
    assert("1,0 4,0 +" == calculator.postFix("1,0+4,0"));
    assert("1,0 4,0 + 2,0 + 3 +" == calculator.postFix("1,0+4,0+2,0+3"));
    assert("5,0 1,0 -" == calculator.postFix("5,0-1,0"));
    assert("5,0 2,0 - 2 -" == calculator.postFix("5,0-2,0-2"));
    assert("5,0 2,0 *" == calculator.postFix("5,0*2,0"));
    assert("5,0 2,0 * 2 *" == calculator.postFix("5,0*2,0*2"));
    assert("10,0 2,0 /" == calculator.postFix("10,0/2,0"));
    assert("10,0 2,0 / 2 / 10 /" == calculator.postFix("10,0/2,0/2/10"));
    assert("10 10 + 5 2 * -" == calculator.postFix("(10+10)-(5*2)"));
    assert("1,5 2,5 + 3 + 2,5 2,5 + -" == calculator.postFix("(1,5+2,5+3)-(2,5+2,5)"));
    assert("5 0 1 - * 10 * 20 + 5 -" == calculator.postFix("5*-10+20-5"));
    assert("2000 1 2 / +" == calculator.postFix("2000+1/2"));
    assert("2 2 + 4 5 * + 1 1000 / +" == calculator.postFix("2+2+4*5+1/1000"));

    std::cout << "Postfix test finished!\n";
}

void resolve()
{
    Calculator calculator;

    // Inválido
    assert("ERROR" == calculator.resolve(""));
    assert("ERROR" == calculator.resolve(" "));
    assert("ERROR" == calculator.resolve("A"));
    assert("ERROR" == calculator.resolve(",1"));
    assert("ERROR" == calculator.resolve(" 0"));       // não permitir iniciar com espaço
    assert("ERROR" == calculator.resolve("(1 + 2)"));  // input: infix
    // Número
    assert("0,0" == calculator.resolve("0"));
    assert("1,1" == calculator.resolve("1,1"));
    // Expressão
    assert("ERROR" == calculator.resolve("1 0,1 / 0 1 - 1 + /"));  // Divisão por zero
    assert("0,25"  == calculator.resolve("10,0 2,0 / 2 / 10 /"));
    assert("10,0"  == calculator.resolve("10 10 + 5 2 * -"));
    assert("2,0"  == calculator.resolve("1,5 2,5 + 3 + 2,5 2,5 + -"));
    assert("-1,0" == calculator.resolve("0 1 -"));
    assert("0,0"  == calculator.resolve("0 1 - 1 +"));
    assert("-1,0" == calculator.resolve("0 1 + 2 -"));
    assert("6,0" == calculator.resolve("1 2 + 3 +"));
    assert("2,2" == calculator.resolve("1,1 1,1 +"));
    assert("3,1" == calculator.resolve("1 0,1 + 2 +"));
    assert("-14,9" == calculator.resolve("1 0,1 * 15 -"));
    assert("9,0"   == calculator.resolve("1 0,1 / 1 -"));
    assert("-10,0" == calculator.resolve("1 0,1 / 0 1 - * 1 *"));
    assert("5,0"  == calculator.resolve("1 0,1 / 0 1 - * 1 * 15 +"));
    assert("-2,0" == calculator.resolve("1 0,1 / 0 1 - * 5 /"));
    assert("3,9" == calculator.resolve("1 1,1 - 0 5 - 1 + -"));
    assert("0,0" == calculator.resolve("0 0 1 - - 0 2 - 3 + -"));
    assert("-2,0" == calculator.resolve("0 0 1 - + 0 2 - 3 + -"));
    assert("-35,0" == calculator.resolve("5 0 1 - * 10 * 20 + 5 -"));
    assert("2000,5" == calculator.resolve("2000 1 2 / +"));
    assert("24,001" == calculator.resolve("2 2 + 4 5 * + 1 1000 / +"));
    assert("2,0"   == calculator.resolve("1,5 2,5 + 3 + 2,5 2,5 + -"));
    assert("757,0" == calculator.resolve("100 200 + 2 / 5 * 7 +"));
    assert("-4,0" == calculator.resolve("2 3 1 * + 9 -"));
    assert("23,0" == calculator.resolve("10 2 8 * + 3 -"));

    std::cout << "Resolve test finished!\n";
}

void calc()
{
    Calculator calculator;

    assert("ERROR" == calculator.calculate(""));
    assert("ERROR" == calculator.calculate(" "));
    assert("ERROR" == calculator.calculate("A"));
    assert("ERROR" == calculator.calculate("+"));
    assert("ERROR" == calculator.calculate("- "));
    assert("ERROR" == calculator.calculate("( "));
    assert("ERROR" == calculator.calculate("()"));
    assert("ERROR" == calculator.calculate(" *1"));
    assert("ERROR" == calculator.calculate("/ 1"));
    assert("ERROR" == calculator.calculate("1-"));
    assert("ERROR" == calculator.calculate(" 1 - "));
    assert("ERROR" == calculator.calculate(" 2 / * 3"));
    assert("ERROR" == calculator.calculate(" 2 / * + 3"));
    assert("ERROR" == calculator.calculate("(1/0,1)/(-1+1)"));
    assert("1,0" == calculator.calculate(" 1"));
    assert("1,0" == calculator.calculate(" +1"));
    assert("0,0" == calculator.calculate("0"));
    assert("1,0" == calculator.calculate("1"));
    assert("-1,0" == calculator.calculate("-1"));
    assert("0,0"  == calculator.calculate("-1+1"));
    assert("-1,0" == calculator.calculate("0+1-2"));
    assert("6,0" == calculator.calculate("1+2+3"));
    assert("2,2" == calculator.calculate("1,1+1,1"));
    assert("3,1" == calculator.calculate("(1+0,1)+2"));
    assert("-14,9" == calculator.calculate("(1*0,1)-15"));
    assert("9,0"   == calculator.calculate("(1/0,1)+-1"));
    assert("-10,0" == calculator.calculate("(1/0,1)*-1"));
    assert("-10,0" == calculator.calculate("(1/0,1)/-1"));
    assert("3,9" == calculator.calculate("((1+-1,1))+-((-5+1))"));
    assert("0,0" == calculator.calculate("-(((-1)))+-(((-2+3)))"));
    assert("-35,0"  == calculator.calculate("5*-10+20-5"));
    assert("2000,5" == calculator.calculate("2000+1/2"));
    assert("24,001" == calculator.calculate("2+2+4*5+1/1000"));
    assert("23,0"   == calculator.calculate("((10 + (2 * 8)) - 3)"));
    assert("0,666667" == calculator.calculate("2 / 3"));

    std::cout << "Calc test finished!\n";
}

void extra()
{
    // Outro delimitador
    Calculator t1('|');
    auto value = t1.postFix("1+2*5,0+-1");
    assert("1|2|5,0|*|+|1|-" == value);
    auto result = t1.resolve(value);
    assert("10,0" == result);

    std::cout << "Extra test finished!\n";
}
