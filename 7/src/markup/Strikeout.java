package markup;

import java.util.List;

public class Strikeout extends AbstractTextElement{
    public Strikeout(List<Element> elements){
        super(elements);
        this.setMarkdownRules("~","~");
        this.setHtmlRules("<s>","</s>");
    }
}