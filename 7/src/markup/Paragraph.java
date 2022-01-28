package markup;

import java.util.List;

public class Paragraph {
    protected List<Element> elements;

    public Paragraph(List<Element> var1) {
        this.elements = var1;
    }

    public void toMarkdown(StringBuilder var1) {

        for (Element var3 : this.elements) {
            var1.append(var3.getTextMarkdown());
        }


    }

    public void toHtml(StringBuilder var1) {

        for (Element var3 : this.elements) {
            var1.append(var3.getTextHtml());
        }

    }
}
