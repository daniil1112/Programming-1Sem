package markup;

import java.util.List;

public abstract class AbstractTextElement implements Element {
    protected String startRuleMarkdown;
    protected String endRuleMarkdown;
    protected String startRuleHtml;
    protected String endRuleHtml;
    protected List<Element> elements;

    public AbstractTextElement(List<Element> elements){
        this.elements = elements;
    }

    public void setHtmlRules(String start, String end){
        this.startRuleHtml = start;
        this.endRuleHtml = end;
    }
    public void setMarkdownRules(String start, String end){
        this.startRuleMarkdown = start;
        this.endRuleMarkdown = end;
    }
    public StringBuilder getTextMarkdown(){
        StringBuilder res = new StringBuilder();
        res.append(this.startRuleMarkdown);
        for (Element el: elements){
            res.append(el.getTextMarkdown());
        }
        res.append(this.endRuleMarkdown);
        return res;
    }
    public StringBuilder getTextHtml(){
        StringBuilder res = new StringBuilder();
        res.append(this.startRuleHtml);
        for (Element el: elements){
            res.append(el.getTextHtml());
        }
        res.append(this.endRuleHtml);
        return res;
    }
}
