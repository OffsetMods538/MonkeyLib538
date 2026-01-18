package top.offsetmonkey538.monkeylib538.common.api.annotation;

import java.lang.annotation.*;

/**
 * Indicates that the annotated element is not part of a public API and must not be used outside the library itself.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.PACKAGE})
public @interface Internal {

}
