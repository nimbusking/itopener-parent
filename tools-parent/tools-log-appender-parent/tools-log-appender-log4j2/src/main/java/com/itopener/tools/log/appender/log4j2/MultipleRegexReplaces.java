package com.itopener.tools.log.appender.log4j2;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.pattern.RegexReplacement;

/**
 * MultipleRegexReplaces
 *
 * @author kemi
 * @version 1.0
 * @since 2023/9/25 15:35
 */
@Plugin(name = "replaces", category = "Core", printObject = true)
public class MultipleRegexReplaces {

    private final RegexReplacement[] replaces;

    private MultipleRegexReplaces(RegexReplacement[] replaces) {
        this.replaces = replaces;
    }

    public String format(String msg) {
        for (RegexReplacement regexReplacement : replaces) {
            msg = regexReplacement.format(msg);
        }
        return msg;
    }

    /**
     * 插件创建
     * @param replaces 插件内容
     * @return
     */
    @PluginFactory
    public static MultipleRegexReplaces createRegexReplacement(@PluginElement("replaces") final RegexReplacement[] replaces) {
        if (replaces == null || replaces.length < 1) {
            return null;
        }
        return new MultipleRegexReplaces(replaces);
    }
}
