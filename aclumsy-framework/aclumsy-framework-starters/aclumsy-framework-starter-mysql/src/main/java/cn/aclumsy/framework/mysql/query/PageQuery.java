package cn.aclumsy.framework.mysql.query;

import cn.aclumsy.framework.common.constant.MagicalConstant;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 分页基类 支持排序
 * @author Aclumsy
 * @date 2022-04-22 14:09
 * @since 1.0.0
 */
@Data
@SuppressWarnings("unused")
public class PageQuery {

    //@ApiModelProperty(value = "页码", required = true)
    @Min(value = 1, message = "分页页码不能小于1")
    private Integer pageIndex = 1;

    //@ApiModelProperty(value = "分页长度", required = true)
    @Min(value = 1, message = "分页大小不能小于1")
    private Integer pageSize = 10;

    //@ApiModelProperty(value = "排序（在字段名后加“:asc或:desc”指定升序（降序），多个字段使用逗号分隔，省略排序默认使用升序）", example = "“字段1,字段2” 或者 “字段1:asc,字段2:desc”")
    private String order;

    /**
     * 转成Page对象
     * 排序sort传参格式：“字段1,字段2”（升序） 或者 “字段1:asc,字段2:desc”（指定顺序），asc和desc大小写严格区分
     */
    public <T> Page<T> page() {
        Page<T> page = new Page<>(this.pageIndex, this.pageSize);
        if (StringUtils.isNotEmpty(order)) {
            String[] orderList = order.split(MagicalConstant.COMMA);

            List<OrderItem> orderItemList = Stream.of(orderList).map(o -> {
                String[] arr = o.split(MagicalConstant.COLON);
                boolean isAsc = arr.length == 1 || "asc".equalsIgnoreCase(arr[1]);
                return new OrderItem(arr[0], isAsc);
            }).collect(Collectors.toList());
            page.setOrders(orderItemList);
        }
        return page;
    }

}
