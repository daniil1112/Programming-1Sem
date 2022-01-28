package md2html;


public class MainElement implements Element{
    String startReplace;
    String endReplace;
    StringBuilder resultString = new StringBuilder();
    final String inputString;
    public int countOneStrongStar = 0;
    public int countOneStrong = 0;

    public MainElement(String input){
        this.inputString = input;
    }

    public StringBuilder toHTML() {
        this.countOneStrongRules();
        boolean lastEscape = false;
        boolean openStrongStar = false;
        boolean openStrong = false;
        boolean openEmphasis = false;
        boolean openEmphasisStar = false;
        boolean openCode = false;
        boolean openS = false;
        boolean openQuote = false;
        int i = 0;
        this.resultString.append(this.startReplace);
        while (i < this.inputString.length()){
            if (this.inputString.charAt(i) == '\\'){
                lastEscape = true;
                i++;
            } else if (lastEscape){
                this.addSymbol(this.inputString.charAt(i));
                lastEscape = false;
                i++;
            } else if (this.inputString.charAt(i) == '`'){
                this.resultString.append(openCode?"</code>":"<code>");
                openCode = !openCode;
                i++;
            } else if (this.inputString.charAt(i) == '\''){
                if ((i<this.inputString.length()-1) && (this.inputString.charAt(i)==this.inputString.charAt(i+1))){
                    this.resultString.append(openQuote?"</q>":"<q>");
                    openQuote = !openQuote;
                    i++;
                } else {
                    this.addSymbol(this.inputString.charAt(i));
                }
                i++;
            } else if (this.inputString.charAt(i) == '-'){
                if (i<this.inputString.length()-1){
                    if (this.inputString.charAt(i)==this.inputString.charAt(i+1)){
                        this.resultString.append(openS?"</s>":"<s>");
                        openS = !openS;
                        i++;
                    } else {
                        this.addSymbol(this.inputString.charAt(i));
                    }
                } else {
                    this.addSymbol(this.inputString.charAt(i));
                }
                i++;
            } else if (this.inputString.charAt(i) == '*'){
                if (i<this.inputString.length()-1){
                    if (this.inputString.charAt(i)==this.inputString.charAt(i+1)){
                        this.resultString.append(openStrongStar?"</strong>":"<strong>");
                        openStrongStar = !openStrongStar;
                        i++;
                    } else {
                        if (!openEmphasisStar){
                            if (this.countOneStrongStar > 1){
                                this.countOneStrongStar--;
                                this.resultString.append("<em>");
                                openEmphasisStar = true;
                            } else {
                                this.addSymbol(this.inputString.charAt(i));
                            }
                        } else {
                            this.countOneStrongStar--;
                            this.resultString.append("</em>");
                            openEmphasisStar = false;
                        }
                    }
                } else {
                    if (openEmphasisStar){
                        this.resultString.append("</em>");
                        this.countOneStrongStar--;
                        openEmphasisStar = false;
                    } else {
                        this.addSymbol(this.inputString.charAt(i));
                    }

                }
                i++;
            } else if (this.inputString.charAt(i) == '_'){
                if (i<this.inputString.length()-1){
                    if (this.inputString.charAt(i)==this.inputString.charAt(i+1)){
                        this.resultString.append(openStrong?"</strong>":"<strong>");
                        openStrong = !openStrong;
                        i++;
                    } else {
                        if (!openEmphasis){
                            if (this.countOneStrong > 1){
                                this.countOneStrong--;
                                this.resultString.append("<em>");
                                openEmphasis = true;
                            } else {
                                this.addSymbol(this.inputString.charAt(i));
                            }
                        } else {
                            this.countOneStrong--;
                            this.resultString.append("</em>");
                            openEmphasis = false;
                        }
                    }
                } else {
                    if (openEmphasis){
                        this.resultString.append("</em>");
                        this.countOneStrong--;
                    } else {
                        this.addSymbol(this.inputString.charAt(i));
                    }
                }
                i++;
            } else {
                this.addSymbol(this.inputString.charAt(i));
                i++;
            }
        }
        this.resultString.append(this.endReplace);

        return resultString;
    }

    private void addSymbol(char ch){
        switch (ch){
            case '<':
                this.resultString.append("&lt;");
                break;
            case '>':
                this.resultString.append("&gt;");
                break;
            case '&':
                this.resultString.append("&amp;");
                break;
            default:
                this.resultString.append(ch);
        }
    }

    public void setRules(String start, String end) {
        this.startReplace = start;
        this.endReplace = end;
    }
    private void countOneStrongRules(){
        StringBuilder currentRule = new StringBuilder();
        boolean lastEscape = false;
        for (int i = 0; i<this.inputString.length(); i++){
            char currentCh = this.inputString.charAt(i);
            if (currentCh == '\\'){
                lastEscape = true;
            } else if (lastEscape){
                lastEscape = false;
            } else {
                if (currentCh == '*' || currentCh == '_'){
                    if (currentRule.length() == 0){
                        currentRule.append(currentCh);
                    } else if (currentRule.charAt(0) != currentCh){
                        if (currentRule.charAt(0) == '*'){
                            this.countOneStrongStar++;
                        } else {
                            this.countOneStrong++;
                        }
                        currentRule = new StringBuilder();
                    } else {
                        currentRule.append(currentCh);
                    }

                } else {
                    if (currentRule.length() == 1){
                        if (currentRule.charAt(0) == '*'){
                            countOneStrongStar++;
                        } else {
                            countOneStrong++;
                        }
                        currentRule = new StringBuilder();
                    }
                }
            }
            if (currentRule.length() > 1){
                currentRule = new StringBuilder();
            }
        }
    }
}
