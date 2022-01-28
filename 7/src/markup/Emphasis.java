package markup;

import java.util.List;

public class Emphasis extends AbstractTextElement{
    public Emphasis(List<Element> elements){
        super(elements);
        this.setMarkdownRules("*","*");
        this.setHtmlRules("<em>","</em>");
    }
}
