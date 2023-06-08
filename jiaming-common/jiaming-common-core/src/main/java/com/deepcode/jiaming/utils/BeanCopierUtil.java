package com.deepcode.jiaming.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.cglib.beans.BeanCopier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 属性拷贝工具类
 *
 * @author winmanboo
 * @date 2023/6/8 18:08
 * @see BeanCopier
 */
@UtilityClass
public class BeanCopierUtil {
    /**
     * 属性拷贝
     * <br/>
     * 注意：该方法要求目标类必须有无参构造器，否则无法生成对应的对象
     *
     * @param source 源对象
     * @param target 要转换的目标类
     * @param <K>    源类型
     * @param <T>    目标类型
     * @return 目标对象
     */
    @Nonnull
    @SneakyThrows
    public static <K, T> T copy(@Nonnull K source, @Nonnull Class<T> target) {
        BeanCopier copier = createCopierWithoutConverter(source.getClass(), target);
        T res = target.getDeclaredConstructor().newInstance();
        copier.copy(source, res, null);
        return res;
    }

    /**
     * 属性拷贝列表
     * <br/>
     * 注意：该方法要求目标类必须有无参构造器，否则无法生成对应的对象
     *
     * @param sources 源对象列表
     * @param target  要转换的目标类
     * @param <K>     源类型
     * @param <T>     目标类型
     * @return 目标对象列表
     */
    @Nonnull
    @SneakyThrows
    public static <K, T> List<T> copyList(@Nonnull List<K> sources, @Nonnull Class<T> target) {
        if (CollUtil.isEmpty(sources)) return Collections.emptyList();

        List<T> targets = new ArrayList<>();

        for (K source : sources) {
            T res = copy(source, target);
            targets.add(res);
        }

        return targets.isEmpty() ? Collections.emptyList() : targets;
    }

    /**
     * 创建 BeanCopier 不使用 Converter
     *
     * @param source 源类对象
     * @param target 目标类对象
     * @param <K>    源类型
     * @param <T>    目标类型
     * @return BeanCopier
     */
    private <K, T> BeanCopier createCopierWithoutConverter(Class<K> source, Class<T> target) {
        // 考虑是否要将 BeanCopier 添加到内存缓存中
        return BeanCopier.create(source, target, false);
    }
}
