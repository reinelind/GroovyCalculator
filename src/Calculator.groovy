class Calculator {
    static void main (String[] args) {
        def input = System.in.newReader().readLine()
        RPN rpn = new RPN(input)

        println rpn.calculate()
    }
}
