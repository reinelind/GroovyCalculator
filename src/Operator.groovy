class Operator implements Comparable <Operator> {
    int operatorLevel
    String operatorSign;

    Operator(String s) {
        operatorSign = new String(s)
        operatorLevel = (setOperatorLevel(s))
    }
    @Override
    public int compareTo(Operator o) {
        this.operatorLevel >= o.operatorLevel ? 1 : 0
    }

    private int setOperatorLevel (String sign) {
        switch (sign) {
            case '(':
                case ')':
                return 0;
            case '+':
                case '-':
                return 1;
            case '*':
                case '/':
                return 2;
            default:
                throw new IllegalArgumentException("Operator unknown: " + sign);
        }
    }
}
