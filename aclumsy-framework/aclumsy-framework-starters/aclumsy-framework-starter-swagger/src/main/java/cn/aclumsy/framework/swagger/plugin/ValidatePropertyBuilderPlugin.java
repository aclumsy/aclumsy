/**
 * Copyright (c), Aclumsy. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.aclumsy.framework.swagger.plugin;

import cn.aclumsy.framework.swagger.util.SwaggerUtils;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTermType;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenCollector;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.TokenIterator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.validation.Constraint;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Swagger基于 validation 注解生成 提示文档 <br>
 * 就是读取到 validation 注解中的code, 然后从配置文件中 ValidationMessages 读取到具体对应的描述信息<br>
 * 添加到 swagger 参数描述中
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings("all")
public class ValidatePropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

    private final PlatformResourceBundleLocator platformResourceBundleLocator = new PlatformResourceBundleLocator("org.hibernate.validator.ValidationMessages");

    private final ResourceBundle defaultResourceBundle = platformResourceBundleLocator.getResourceBundle(Locale.CHINA);

    @Override
    public void apply(ModelPropertyContext context) {
        try {
            context.getBeanPropertyDefinition()
                    .map(BeanPropertyDefinition::getField)
                    .map(AnnotatedField::getAnnotated)
                    .ifPresent(field -> {
                        Annotation[] annotations = field.getAnnotations();
                        String message = Stream.of(annotations)
                                .filter(annotation -> annotation.annotationType().isAnnotationPresent(Constraint.class))
                                .map(this::annotationToMessage).collect(Collectors.joining(","));
                        SwaggerUtils.addTo(context, message);
                    });
        } catch (Exception e) {
            logger.error("追加 validation 注解描述失败", e);
        }
    }

    private String annotationToMessage(Annotation annotation) {
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        String message = annotationAttributes.get("message").toString();
        if (message.indexOf("{") != 0) {
            return message;
        }
        String resolvedMessage = defaultResourceBundle.getString(removeCurlyBraces(message));
        TokenIterator tokenIterator = new TokenIterator(new TokenCollector(resolvedMessage, InterpolationTermType.PARAMETER).getTokenList());
        while (tokenIterator.hasMoreInterpolationTerms()) {
            String term = tokenIterator.nextInterpolationTerm();
            String resolvedExpression = annotationAttributes.get(removeCurlyBraces(term)).toString();
            tokenIterator.replaceCurrentInterpolationTerm(resolvedExpression);
        }
        return tokenIterator.getInterpolatedMessage();
    }

    private String removeCurlyBraces(String parameter) {
        return parameter.substring(1, parameter.length() - 1);
    }

    @Override
    public boolean supports(@NotNull DocumentationType documentationType) {
        return true;
    }


}
