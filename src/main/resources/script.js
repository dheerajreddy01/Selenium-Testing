document.addEventListener('DOMContentLoaded', function () {
    const keys = document.querySelector('.calculator-keys');
    const screen = document.querySelector('#screen');
    
    let displayValue = '0';
    let firstValue = null;
    let operator = null;
    let waitingForSecondValue = false;

    keys.addEventListener('click', function (event) {
        const { target } = event;
        const { value } = target;

        if (!target.matches('button')) {
            return;
        }

        switch (value) {
            case '+':
            case '-':
            case '*':
            case '/':
                handleOperator(value);
                break;
            case '=':
                handleEqualSign();
                break;
            case '.':
                inputDecimal();
                break;
            case 'all-clear':
                resetCalculator();
                break;
            default:
                inputDigit(value);
        }

        updateScreen();
    });

    function updateScreen() {
        screen.value = displayValue;
    }

    function handleOperator(nextOperator) {
        const value = parseFloat(displayValue);

        if (operator && waitingForSecondValue) {
            operator = nextOperator;
            return;
        }

        if (firstValue === null) {
            firstValue = value;
        } else if (operator) {
            const result = calculate(firstValue, value, operator);
            displayValue = `${parseFloat(result.toFixed(7))}`;
            firstValue = result;
        }

        waitingForSecondValue = true;
        operator = nextOperator;
    }

    function handleEqualSign() {
        const value = parseFloat(displayValue);

        if (operator && waitingForSecondValue) {
            operator = null;
            return;
        }

        if (firstValue === null) {
            return;
        } else if (operator) {
            const result = calculate(firstValue, value, operator);
            displayValue = `${parseFloat(result.toFixed(7))}`;
            firstValue = null;
            operator = null;
            waitingForSecondValue = false;
        }
    }

    function calculate(first, second, operator) {
        if (operator === '+') {
            return first + second;
        } else if (operator === '-') {
            return first - second;
        } else if (operator === '*') {
            return first * second;
        } else if (operator === '/') {
            return first / second;
        }

        return second;
    }

    function inputDigit(digit) {
        if (waitingForSecondValue) {
            displayValue = digit;
            waitingForSecondValue = false;
        } else {
            displayValue = displayValue === '0' ? digit : displayValue + digit;
        }
    }

    function inputDecimal() {
        if (!displayValue.includes('.')) {
            displayValue += '.';
        }
    }

    function resetCalculator() {
        displayValue = '0';
        firstValue = null;
        operator = null;
        waitingForSecondValue = false;
    }
});
