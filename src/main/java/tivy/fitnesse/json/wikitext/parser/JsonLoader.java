package tivy.fitnesse.json.wikitext.parser;

import fitnesse.wikitext.parser.Matcher;
import fitnesse.wikitext.parser.Parser;
import fitnesse.wikitext.parser.Rule;
import fitnesse.wikitext.parser.Symbol;
import fitnesse.wikitext.parser.SymbolType;
import fitnesse.wikitext.parser.Translation;
import fitnesse.wikitext.parser.Translator;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import util.Maybe;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tibor Vyletel on 2014-12-07.
 */
public class JsonLoader extends SymbolType implements Rule, Translation {

    public JsonLoader() {
        super("JsonLoader");
        wikiMatcher(new Matcher().startLineOrCell().string("!json"));
        wikiRule(this);
        htmlTranslation(this);
    }

    @Override
    public Maybe<Symbol> parse(Symbol current, Parser parser) {
        if (!parser.isMoveNext(SymbolType.Whitespace)) return Symbol.nothing;

        Maybe<String> resource = parser.parseToAsString(SymbolType.Whitespace);
        if (resource.isNothing()) return Symbol.nothing;

        final String resourceName = resource.getValue();
        String loadedJson;
        try {
            final InputStream stream = getClass().getResourceAsStream(resourceName);
            loadedJson = IOUtils.toString(stream);
        } catch (IOException ioe) {
            return Symbol.nothing;
        }

        parser.getPage().putVariable(StringUtils.substringAfterLast(resourceName, "/"), loadedJson);
        return new Maybe<Symbol>(current.add(resourceName).add(loadedJson));

    }

    @Override
    public String toTarget(Translator translator, Symbol symbol) {
        return null;
    }
}
