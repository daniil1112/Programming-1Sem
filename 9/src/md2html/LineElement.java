package md2html;

public class LineElement {
    public Element getElement(StringBuilder str){
        boolean isHeader = false;
        int hashCounter = 0;
        Element res;
        if (str.toString().charAt(0) == '#') {
            for (int i = 0; i < str.length(); i++){
                if (str.toString().charAt(i) == '#'){
                    hashCounter++;
                } else if (Character.isWhitespace(str.toString().charAt(i))){
                    isHeader = true;
                    break;
                } else {
                    break;
                }
            };
        }

        if (isHeader && hashCounter > 0){
            res = new Header(str.substring(hashCounter+1, str.length()), hashCounter);
        } else {
            res = new Paragraph(str.toString());
        }

        return res;
    }
}
