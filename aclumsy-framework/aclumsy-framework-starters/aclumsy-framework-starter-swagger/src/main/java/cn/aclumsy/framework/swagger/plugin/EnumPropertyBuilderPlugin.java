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

import cn.aclumsy.framework.common.model.IDict;
import cn.aclumsy.framework.swagger.annotation.ApiModelPropertyEnum;
import cn.aclumsy.framework.swagger.util.SwaggerUtils;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Swagger枚举属性增强插件<br>
 * 目的就是 将该属性的所表示的枚举值的信息,追加到该参数属性的描述中
 * @author Aclumsy
 * @date 2022-04-22 18:40
 * @since 1.0.0
 */

@Slf4j
public class EnumPropertyBuilderPlugin implements ModelPropertyBuilderPlugin, ExpandedParameterBuilderPlugin {


    /**
     * 枚举类型属性自动扩展注释
     * 适用于非 GET 请求接口中 DTO 的属性（有注解 @RequestBody 标识）
     *
     * @param context context
     */
    @Override
    public void apply(ModelPropertyContext context) {
        context.getBeanPropertyDefinition()
                .map(BeanPropertyDefinition::getField)
                .map(AnnotatedField::getAnnotated)
                .ifPresent(field -> {
                    ApiModelPropertyEnum apiModelPropertyEnum = field.getAnnotation(ApiModelPropertyEnum.class);
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    if (apiModelPropertyEnum != null && apiModelProperty != null) {
                        SwaggerUtils.addTo(context, this.getDescription(apiModelPropertyEnum));
                    }
                });
    }

    /**
     * 枚举类型属性自动扩展注释
     * 适用于 GET 请求参数中 DTO 的属性
     *
     * @param context context
     */
    @Override
    public void apply(ParameterExpansionContext context) {
        Optional<ApiModelProperty> apiModelPropertyOptional = context.findAnnotation(ApiModelProperty.class);
        Optional<ApiModelPropertyEnum> apiModelPropertyEnumOptional = context.findAnnotation(ApiModelPropertyEnum.class);
        if (apiModelPropertyOptional.isPresent() && apiModelPropertyEnumOptional.isPresent()) {
            ApiModelPropertyEnum apiModelPropertyEnum = apiModelPropertyEnumOptional.get();
            context.getRequestParameterBuilder().description(this.getDescription(apiModelPropertyEnum));
        }
    }

    @Override
    public boolean supports(@NotNull DocumentationType documentationType) {
        return true;
    }

    /**
     * 增强注释，将枚举信息添加到原注释之后
     */
    private String getDescription(ApiModelPropertyEnum apiModelPropertyEnum) {
        Class<? extends IDict<?>> dictEnum = apiModelPropertyEnum.dictEnum();
        return Arrays.stream(dictEnum.getEnumConstants()).map(dict -> dict.getCode() + "：" + dict.getText()).collect((Collectors.joining("，")));
    }


}
