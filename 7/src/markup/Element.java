package markup;

public interface Element {
    StringBuilder getTextMarkdown();
    StringBuilder getTextHtml();
    void setHtmlRules(String start, String end);
    void setMarkdownRules(String start, String end);
}
