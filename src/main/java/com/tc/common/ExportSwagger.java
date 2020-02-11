package com.tc.common;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author
 * @since 2019/01/22
 */
public class ExportSwagger {
    /**
     * 生成AsciiDocs格式文档
     * @throws Exception
     */
    @Test
    public void generateAsciiDocs() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        // swagger-ui.html页面中能找到此链接
        Swagger2MarkupConverter.from(new URL("http://localhost:8088/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("/docs"));
    }

    /**
     * 生成Markdown格式文档
     * @throws Exception
     */
    @Test
    public void generateMarkdownDocs() throws Exception {
        //    输出Markdown格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8088/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("/docs"));
    }
  /**
   * 生成Confluence格式文档
   * @throws Exception
   */
  @Test
  public void generateConfluenceDocs() throws Exception {
      //    输出Confluence使用的格式
      Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
              .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
              .withOutputLanguage(Language.ZH)
              .withPathsGroupedBy(GroupBy.TAGS)
              .withGeneratedExamples()
              .withoutInlineSchema()
              .build();

      Swagger2MarkupConverter.from(new URL("http://localhost:8088/v2/api-docs"))
              .withConfig(config)
              .build()
              .toFolder(Paths.get("/docs/confluence/generated"));
  }

    /**
     * 生成AsciiDocs格式文档,并汇总成一个文件
     * @throws Exception
     */
    @Test
    public void generateAsciiDocsToFile() throws Exception {
        //    输出Ascii到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8088/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("/docs/"));
    }


    /**
     * 生成Markdown格式文档,并汇总成一个文件
     * @throws Exception
     */
    @Test
    public void generateMarkdownDocsToFile() throws Exception {
        //    输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8088/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("/docs"));
    }
}
