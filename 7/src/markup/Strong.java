package markup;

import java.util.List;

public class Strong extends AbstractTextElement{
    public Strong(List<Element> elements){
        super(elements);
        this.setMarkdownRules("__","__");
        this.setHtmlRules("<strong>","</strong>");
    }
}
