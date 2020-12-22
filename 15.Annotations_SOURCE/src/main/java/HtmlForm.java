import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface HtmlForm {
    String method() default "get";
    String action() default "/";
}
