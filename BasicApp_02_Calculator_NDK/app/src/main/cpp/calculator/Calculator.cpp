#include "Calculator.hpp"

Calculator::Calculator()
{
    currentCharacters = ALL;
    currentDelimiter  = DELIMITER;
}

Calculator::Calculator(char delimiter)
{
    setCurrentDelimiter(delimiter);
}

void Calculator::setCurrentDelimiter(const char delimiter)
{
    currentDelimiter = delimiter;
    currentCharacters = POSTFIX + LPARENTHESES + RPARENTHESES + delimiter;
}

auto Calculator::calculator(const std::string &expression) -> std::string
{
    return Calculator().calculate(expression);
}

auto Calculator::calculate(const std::string &expression) -> std::string
{
    return resolve(postFix(expression));
}

auto Calculator::resolve(const std::string &postfixExpression) -> std::string
{
    if (isEmpty(postfixExpression)) {
        return ERROR;
    }

    if (!expressionIsValid(postfixExpression, true)) {
        return ERROR;
    }

    std::cout << postfixExpression << '\n';

    std::list<std::string> tokens;
    for (const auto& token : split(postfixExpression, currentDelimiter)) {
        if (!replace(token, SPACE, EMPTY).empty()) {
            tokens.emplace_back(token);
        }
    }

    if (tokens.empty()) {
        return ERROR;
    }

    std::stack<double> numbers;
    for (const auto &token : tokens) {
        if (contains(OPERATOR, token)) {
            if (numbers.empty()) {
                return ERROR;
            }
            auto operand2 = numbers.top();
            numbers.pop();
            if (numbers.empty()) {
                return ERROR;
            }
            auto operand1 = numbers.top();
            numbers.pop();
            if (token == "+") {
                numbers.push(operand1 + operand2);
            }
            else if (token == "-") {
                numbers.push(operand1 - operand2);
            }
            else if (token ==  "*") {
                numbers.push(operand1 * operand2);
            }
            else if (token == "/") {
                if (operand2 != 0) {
                    numbers.push(operand1 / operand2);
                }
                else {
                    return ERROR;
                }
            }
        }
        else {
            try {
                auto num = replace(token, SEPARATOR, DOT);
                numbers.push(std::stod(num));
            }
            catch (std::exception e) {
                return ERROR;
            }
        }
    }

    if (numbers.size() != 1) {
        return ERROR;
    }

    double num = numbers.top();
    std::string result = rtrimZeros(replace(std::to_string(num), DOT, SEPARATOR));
    std::vector<std::string> array = split(result, SEPARATOR);
    if (result.back() == SEPARATOR) {
        result += "0";
    }

    return result;
}

auto Calculator::precedence(const char key) -> int
{
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

auto Calculator::postFix(std::string expression) -> std::string
{
    if (isEmpty(expression)) {
        return ERROR;
    }

    expression = prepare(expression);
    if (!expressionIsValid(expression)) {
        return ERROR;
    }

    // Converter
    std::stack<char> stack;
    std::string postfixExpression{currentDelimiter};

    for (const auto &c : expression) {

        if (c == DELIMITER) {
            continue;
        }

        if (contains(NUMBER, c)) {
            postfixExpression.push_back(c);
            continue;
        }

        if (postfixExpression.back() != currentDelimiter) {
            postfixExpression.push_back(currentDelimiter);
        }

        if (c == LPARENTHESES) {
            stack.push(c);
        }
        if (c == RPARENTHESES) {
            while (stack.top() != LPARENTHESES) {
                if (postfixExpression.back() != currentDelimiter) {
                    postfixExpression.push_back(currentDelimiter);
                }
                postfixExpression.push_back(stack.top());
                stack.pop();
            }
            stack.pop();
        }

        if (contains(OPERATOR, c)) {
            while (!stack.empty() && stack.top() != LPARENTHESES && precedence(c) <= precedence(stack.top())) {
                if (postfixExpression.back() != currentDelimiter) {
                    postfixExpression.push_back(currentDelimiter);
                }
                postfixExpression.push_back(stack.top());
                postfixExpression.push_back(currentDelimiter);
                stack.pop();
            }
            stack.push(c);
        }
    }

    while (!stack.empty()) {
        if (postfixExpression.back() != currentDelimiter) {
            postfixExpression.push_back(currentDelimiter);
        }
        postfixExpression.push_back(stack.top());
        stack.pop();
    }

    if (postfixExpression.size() > 1) {
        if (postfixExpression.front() == currentDelimiter) {
            postfixExpression = postfixExpression.substr(1);
        }
    }

    return postfixExpression;
}

auto Calculator::prepare(std::string expression) const -> std::string
{
    if (isEmpty(expression)) {
        return {};
    }

    // Remover espaços
    expression = replace(expression, SPACE, EMPTY);

    // Tratar sinal no início
    if (expression.front() == POSITIVE || expression.front() == NEGATIVE) {
        expression = "0" + expression;
    }

    // Tratar números com sinais
    expression = replace(expression, "--", "+");
    expression = replace(expression, "+-", "-");
    expression = replace(expression, "*+", "*");
    expression = replace(expression, "/+", "/");
    expression = replace(expression, "*-", "*(0-1)*");
    expression = replace(expression, "/-", "*(0-1)/");
    expression = replace(expression, "(-", "(0-");

    return expression;
}

auto Calculator::expressionIsValid(const std::string &expression, bool isPostFix) -> bool
{
    if (isEmpty(expression)) {
        return false;
    }

    std::string first = DIGITS + SIGNAL + LPARENTHESES;
    std::string last  = DIGITS + RPARENTHESES;

    if (isPostFix) {
        first = DIGITS;
        last  = DIGITS + OPERATOR;
    }

    if (!contains(first, expression.front())) {
        return false;
    }

    if (!contains(last, expression.back())) {
        return false;
    }

    if (!isPostFix) {
        int digitCounter = 0;
        int leftParenthesisCounter  = 0;
        int rightParenthesisCounter = 0;
        for (const auto &c : expression) {
            if (!contains(currentCharacters, c)) {
                return false;
            }
            if (contains(DIGITS, c)) {
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

auto Calculator::isNumber(const std::string &value) -> bool
{
    if (isEmpty(value)) {
        return false;
    }

    try {
        auto flags = std::regex_constants::ECMAScript | std::regex_constants::icase;
        if (std::regex_search(value, std::regex("^[+-]?[0-9]+\\" + std::string{SEPARATOR} + "[0-9]+$", flags))) {
            return true;    // Is float.
        }
        return std::regex_search(value, std::regex("^[+-]?[0-9]+$", flags));    // Is integer.
    }
    catch (...) {
        std::cerr << "REGEX ERROR: " << value << '\n';
    }

    return false;
}

auto Calculator::isEmpty(const std::string &expression) const -> bool
{
    return replace(expression, SPACE, EMPTY).empty();
}

auto Calculator::replace(const std::string &str, char character, char newCharacter) -> std::string
{
    std::string result{};
    for (int i = 0; i < str.size(); ++i) {
        if (str[i] == character) {
            if (newCharacter != '\0') {
                result += newCharacter;
            }
        }
        else {
            result += str[i];
        }
    }

    return result;
}

auto Calculator::replace(const std::string &str, const std::string &characters, const std::string &strReplace) -> std::string
{
    if (str.size() < characters.size()) {
        return str;
    }

    if (str == characters) {
        return strReplace;
    }

    std::string result{};
    for (size_t i = 0; i < str.size(); ++i) {
        auto substr = str.substr(i, characters.size());
        if (substr == characters) {
            result.append(strReplace);
            i += characters.size() - 1;
        }
        else {
            result.push_back(str[i]);
        }
    }

    return result;
}

auto Calculator::split(const std::string &str, const char delimiter) -> std::vector<std::string>
{
    std::vector<std::string> result;

    std::string strTemp{};
    for (char character : str) {
        if (character == delimiter) {
            result.push_back(strTemp);
            strTemp.clear();
        }
        else {
            strTemp += character;
        }
    }
    if (!strTemp.empty()) {
        result.push_back(strTemp);
    }

    return result;
}

auto Calculator::contains(const std::string &str, const char &character) -> bool
{
    for (const auto &c : str) {
        if (c == character) {
            return true;
        }
    }

    return false;
}

auto Calculator::contains(const std::string &str, const std::string &characters) -> bool
{
    if (characters.size() == 1) {
        return contains(str, characters.front());
    }

    if (characters.size() > str.size()) {
        return false;
    }

    if (characters == str) {
        return true;
    }

    auto size = str.size() - characters.size();
    for (int i = 0; i < size; ++i) {
        auto substr = str.substr(i, characters.size());
        if (substr == characters) {
            return true;
        }
    }

    return false;
};

auto Calculator::rtrimZeros(const std::string &str) -> std::string
{
    int left = 0;
    auto right = str.size() - 1;
    right = right < 0 ? 0 : right;
    while (right >= 0) {
        if (str[right] != '0') {
            break;
        }
        right--;
    }

    return str.substr(left, 1 + right - left);
}
