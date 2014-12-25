package tivy.fitnesse.json.wikitext.parser;

import fitnesse.wiki.WikiPage;
import fitnesse.wikitext.parser.Parser;
import fitnesse.wikitext.parser.ParsingPage;
import fitnesse.wikitext.parser.SymbolProvider;
import fitnesse.wikitext.parser.WikiSourcePage;
import org.junit.Test;
import tivy.fitnesse.testutils.TestRoot;

import static org.junit.Assert.*;

public class JsonLoaderTest {

    private static final SymbolProvider provider = SymbolProvider.wikiParsingProvider.add(new JsonLoader());

    @Test
    public void definesValues () {
        assertDefinesValue("!json /jsons/empty.json ", "empty.json", "{}");
    }

    private void assertDefinesValue(String input, String name, String definedValue) {
        WikiPage pageOne = new TestRoot().makePage("PageOne", input);
        ParsingPage page = new ParsingPage(new WikiSourcePage(pageOne));
        Parser.make(page, input, provider).parse();
        assertEquals(definedValue, page.findVariable(name).getValue());
    }

}