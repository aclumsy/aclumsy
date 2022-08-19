package cn.aclumsy.framework.common.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List工具类
 *
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
@UtilityClass
public class ListUtil {
    /**
     * 匹配两个list集合相同元素，并执行匹配后的动作
     * 适用于两个集合泛型不一致
     *
     * @param list1    第一个集合
     * @param list2    第二个集合
     * @param field1   第一个集合中判定元素相同的属性，如：SomeBean::getId
     * @param field2   第二个集合中判定元素相同的属性，如：OtherBean::getId
     * @param operator 匹配后执行的动作，入参为A、B集合中的相同元素，如：(a, b)->a.setXx(b.getXx())
     * @param <T>      第一个集合的泛型
     * @param <K>      第二个集合的泛型
     */
    public static <T, K> void match(Collection<T> list1, Collection<K> list2, Function<T, ?> field1, Function<K, ?> field2, BiConsumer<T, K> operator) {
        Map<?, K> map = list2.stream().collect(Collectors.toMap(field2, Function.identity()));
        list1.forEach(item1 -> Optional.ofNullable(map.get(field1.apply(item1))).ifPresent(item2 -> operator.accept(item1, item2)));
    }

    /**
     * 匹配两个list集合中的相同元素，并执行匹配后的动作
     * 适用于两个集合的泛型一致
     *
     * @param list1      第一个集合
     * @param list2      第二个集合
     * @param equalField 判定元素相同的属性，如Student::getId
     * @param operator   匹配后执行的动作，入参为两个集合中的相同元素，如：(a, b)->a.setXx(b.getXx())
     * @param <T>        集合泛型
     */
    public static <T> void match(Collection<T> list1, Collection<T> list2, Function<T, ?> equalField, BiConsumer<T, T> operator) {
        match(list1, list2, equalField, equalField, operator);
    }

    /**
     * 转换List
     *
     * @param list      原集合
     * @param converter 转换函数
     * @return 转换后的函数
     */
    public static <T, R> List<R> convert(List<T> list, Function<T, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

    /**
     * 转换List，转换之后去重
     *
     * @param list      原集合
     * @param converter 转换函数
     * @return 转换后的函数
     */
    public static <T, R> List<R> convertDistinct(List<T> list, Function<T, R> converter) {
        return list.stream().map(converter).distinct().collect(Collectors.toList());
    }

}
