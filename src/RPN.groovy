class RPN {
    Stack <Operator> operations
    List <String> inputList
    List <String> postfixList
    List <String> outputList
    public RPN (String str) {

        inputList = new ArrayList <String>()
        postfixList = new ArrayList <String> ()
        operations = new Stack <Operator> ()
        outputList = new ArrayList <String> ()
        toList(str)
        toPostfixList(inputList)

    }
    def public toList(String str) {
        def String tmpStr = str.replaceAll("\\s+", "")
            while (tmpStr) {
                if (tmpStr[0] == '(') {
                    inputList.add('(')
                    tmpStr = tmpStr - ~/^\(/
                } else {
                    def String number = tmpStr.takeWhile { it =~ /\d+/ }
                    inputList.add(number)

                    def String operator = tmpStr.find(/[\(\+\-\*\\/\)][\(]?/)
                    try {
                        if (operator.contains('(') && operator.size() == 2) {
                            inputList.add(operator[0])
                            inputList.add(operator[1])
                        } else
                            inputList.add(operator)

                    } catch (NullPointerException n) {
                        inputList.add(" ")
                    }

                    tmpStr = tmpStr - ~/[\+\-\*\\/][\(]?|^[\(]?\d+[\+\-\*\\/]?[\(]?[\)]?^\(|\d+[\+\-\*\\/]?[\(]?[\)]?/

                }
            }

        inputList.removeAll(Collections.singleton(""))
        inputList.removeAll(Collections.singleton(" "))
    }

    def public toPostfixList(List <String> stringList)  {
        for (String s : stringList) {
            if (s.isNumber()) {
                outputList.add(s)
            }
            else {
                if (operations.isEmpty() || s == "(") {
                    operations.push(new Operator(s))
                }
                else if (s == ")") {
                    while (operations.peek().getOperatorSign() != "(") {
                        outputList.add(operations.pop().getOperatorSign())
                    }
                    operations.pop()
                }
                else if (operations.peek() <=> new Operator(s)) {
                    outputList.add((operations.pop().getOperatorSign()))
                    operations.push(new Operator(s))
                } else operations.push(new Operator(s))
            }

        }
        while (!operations.isEmpty()) {
            outputList.add((operations.pop().getOperatorSign()))
        }


    }

    def public String calculate () {
        println outputList
        List resultStack = new Stack <int> ()
        for (ListIterator<String> iter = outputList.listIterator();iter.hasNext();) {
            String element = iter?.next()

            if (outputList.size() == 1) return outputList.pop()
            if (element.isNumber()) {
                resultStack.push(element.toInteger())
            }
            else {
                try {
                    def op1Value = resultStack.pop()
                    def op2Value = resultStack.pop()
                    switch (element) {
                            case '/': {
                                def result = op2Value / op1Value
                                resultStack.push(result)
                                break
                            }
                            case '+': {
                                def result = op1Value + op2Value
                                resultStack.push(result)
                                break
                            }
                            case '-': {
                                def result = op2Value - op1Value
                                resultStack.push(result)
                                break
                            }
                            case '*': {
                                def result = op1Value * op2Value
                                resultStack.push(result)
                                break
                            }
                        }




                }catch (NullPointerException e ) {
                    println e.localizedMessage+"Error in input: not correct number of operands"
                }catch (UnsupportedOperationException e ) {
                    println e.localizedMessage + "Error in calculation: division by zero"
                }
            }
            println resultStack
        }
        try {
            return resultStack.pop()
        } catch (EmptyStackException e) {
            println "Calculation error: You might have entered empty string"

        }
        }
}
