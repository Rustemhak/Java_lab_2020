import com.google.auto.service.AutoService;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm","HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // получить типы с аннотаций HtmlForm
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {
            // получаем полный путь для генерации html
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            // User.class -> User.html
            path = path.substring(1) + element.getSimpleName().toString() + ".html";

            Path out = Paths.get(path);
            String templateName = element.getSimpleName().toString() + ".ftlh";
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDefaultEncoding("UTF-8");
            Map<String, Object> attributes = new HashMap<>();


            try {
                configuration.setTemplateLoader(new FileTemplateLoader(new File("src/main/resourses")));
                Template template  = configuration.getTemplate(templateName);
                HtmlForm annotation = element.getAnnotation(HtmlForm.class);
                List<Map<String, String>> users = new ArrayList<>();
                element.getEnclosedElements().forEach(elem -> {
                    HtmlInput htmlInput = elem.getAnnotation(HtmlInput.class);
                    if (htmlInput != null) {
                        Map<String, String> elemAttributes = new HashMap<>();
                        elemAttributes.put("type", htmlInput.type());
                        elemAttributes.put("name", htmlInput.name());
                        elemAttributes.put("placeholder", htmlInput.placeholder());
                        users.add(elemAttributes);
                    }
                });
                attributes.put("inputs", users);
                attributes.put("action", annotation.action());
                attributes.put("method", annotation.method());

                BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile().getAbsolutePath()));
                try {
                    template.process(attributes,writer);
                } catch (TemplateException e) {
                    throw new IllegalStateException(e);
                }

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        }
        return true;
    }
}
