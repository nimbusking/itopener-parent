package com.itopener.tools.log.appender.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MultipleRegexReplacesPatternLayout
 *
 * @author kemi
 * @version 1.0
 * @since 2023/9/25 15:46
 */
@Plugin(name = "MultipleRegexReplacesPatternLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class MultipleRegexReplacesPatternLayout extends AbstractStringLayout {

    private static final String DEFAULT_CONVERSION_PATTERN = "%msg%n";

    private static final String CONVERTER_KEY = "Converter";

    private final List<PatternFormatter> forMatters;

    private final String conversionPattern;

    private final Configuration config;

    private final MultipleRegexReplaces replace;

    private final boolean alwaysWriteExceptions;

    private final boolean noConsoleNoAnsi;

    private MultipleRegexReplacesPatternLayout(final Configuration config, final MultipleRegexReplaces replace, final String pattern,
                                               final Charset charset, final boolean alwaysWriteExceptions,
                                               final boolean noConsoleNoAnsi, final String header, final String footer) {
        super(charset, toBytes(header, charset), toBytes(footer, charset));
        this.replace = replace;
        this.conversionPattern = pattern;
        this.config = config;
        this.alwaysWriteExceptions = alwaysWriteExceptions;
        this.noConsoleNoAnsi = noConsoleNoAnsi;
        final PatternParser patternParser = createPattenParser(config);
        this.forMatters = patternParser.parse(pattern == null ? DEFAULT_CONVERSION_PATTERN : pattern, this.alwaysWriteExceptions, this.noConsoleNoAnsi);

    }

    public static PatternParser createPattenParser(final Configuration config) {
        if (config == null) {
            return new PatternParser(config, CONVERTER_KEY, LogEventPatternConverter.class);
        }
        PatternParser patternParser = config.getComponent(CONVERTER_KEY);
        if (patternParser == null) {
            patternParser = new PatternParser(config, CONVERTER_KEY, LogEventPatternConverter.class);
            config.addComponent(CONVERTER_KEY, patternParser);
            patternParser = config.getComponent(CONVERTER_KEY);
        }
        return patternParser;
    }

    private static byte[] toBytes(final String str, final Charset charset) {
        if (str != null) {
            return str.getBytes(charset != null ? charset : Charset.defaultCharset());
        }
        return null;
    }

    private byte[] strSubstitutorReplace(final byte...b) {
        if (b != null && config != null) {
            return getBytes(config.getStrSubstitutor().replace(new String(b, getCharset())));
        }
        return b;
    }

    @Override
    public byte[] getFooter() {
        return strSubstitutorReplace(super.getFooter());
    }

    @Override
    public byte[] getHeader() {
        return strSubstitutorReplace(super.getHeader());
    }

    @Override
    public Map<String, String> getContentFormat() {
        final Map<String, String> result = new HashMap<>(8);
        result.put("structured", "false");
        result.put("formatType", "conversion");
        result.put("format", conversionPattern);
        return result;
    }

    @Override
    public String toSerializable(LogEvent event) {
        final StringBuilder builder = new StringBuilder();
        for (final PatternFormatter formatter : forMatters) {
            formatter.format(event, builder);
        }
        String str = builder.toString();
        if (replace != null) {
            str = replace.format(str);
        }
        return str;
    }

    @Override
    public String toString() {
        return conversionPattern;
    }

    @PluginFactory
    public static MultipleRegexReplacesPatternLayout createLayout(@PluginAttribute(value = "pattern", defaultString = DEFAULT_CONVERSION_PATTERN) final String pattern,
                                                                  @PluginConfiguration final Configuration config,
                                                                  @PluginElement("Replaces") final MultipleRegexReplaces replace,
                                                                  @PluginAttribute(value = "charset", defaultString = "UTF-8") final Charset charset,
                                                                  @PluginAttribute(value = "alwaysWriteExceptions", defaultBoolean = true) final boolean alwaysWriteExceptions,
                                                                  @PluginAttribute(value = "noConsoleNoAnsi", defaultBoolean = false) final boolean noConsoleNoAnsi,
                                                                  @PluginAttribute("header") final String header,
                                                                  @PluginAttribute("footer")final String footer) {
        return newBuilder().withPattern(pattern).withConfiguration(config).withRegexReplacement(replace).withCharset(charset).withAlwaysWriteExceptions(alwaysWriteExceptions)
                .withNoConsoleNoAnsi(noConsoleNoAnsi).withHeader(header).withFooter(footer).build();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<MultipleRegexReplacesPatternLayout> {

        @PluginBuilderAttribute
        private String pattern = MultipleRegexReplacesPatternLayout.DEFAULT_CONVERSION_PATTERN;

        @PluginConfiguration
        private Configuration configuration = null;

        @PluginElement("replaces")
        private static MultipleRegexReplaces regexReplacement;

        @PluginBuilderAttribute
        private Charset charset = Charset.defaultCharset();

        @PluginBuilderAttribute
        private boolean alwaysWriteExceptions = true;

        @PluginBuilderAttribute
        private boolean noConsoleNoAnsi = false;

        @PluginBuilderAttribute
        private String header = null;

        @PluginBuilderAttribute
        private String footer = null;

        private Builder(){}

        public Builder withPattern(final String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder withConfiguration(final Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder withRegexReplacement(final MultipleRegexReplaces regexReplacement) {
            Builder.regexReplacement = regexReplacement;
            return this;
        }

        public Builder withCharset(final Charset charset) {
            this.charset = charset;
            return this;
        }

        public Builder withAlwaysWriteExceptions(final boolean alwaysWriteExceptions) {
            this.alwaysWriteExceptions = alwaysWriteExceptions;
            return this;
        }

        public Builder withNoConsoleNoAnsi(final boolean noConsoleNoAnsi) {
            this.noConsoleNoAnsi = noConsoleNoAnsi;
            return this;
        }

        public Builder withHeader(final String header) {
            this.header = header;
            return this;
        }

        public Builder withFooter(final String footer) {
            this.footer = footer;
            return this;
        }



        @Override
        public MultipleRegexReplacesPatternLayout build() {
            if (configuration == null) {
                configuration = new DefaultConfiguration();
            }
            return new MultipleRegexReplacesPatternLayout(configuration, regexReplacement, pattern, charset, alwaysWriteExceptions, noConsoleNoAnsi, header, footer);
        }
    }

}
