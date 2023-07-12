package com.deepcode.jiaming.admin.utils;

import com.deepcode.jiaming.admin.vo.DeptVo;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author winmanboo
 * @date 2023/7/12 18:11
 */
@UtilityClass
public class DeptHelper {
    /**
     * 生成部门树结构
     *
     * @param depts 部门列表
     * @return {@link List}<{@link DeptVo}>
     */
    public static List<DeptVo> generateDeptTree(List<DeptVo> depts) {
        List<DeptVo> deptList = new ArrayList<>();
        depts.forEach(dept -> {
            if (dept.getParentId() == 0) {
                deptList.add(childrenDept(dept, depts));
            }
        });
        return deptList;
    }

    /**
     * 递归获取下级部门
     *
     * @param dept  部门
     * @param depts 部门列表
     * @return 下级部门
     */
    private static DeptVo childrenDept(DeptVo dept, List<DeptVo> depts) {
        List<DeptVo> children = depts.stream().filter(item -> {
            if (Objects.equals(dept.getId(), item.getParentId())) {
                childrenDept(item, depts);
                return true;
            }
            return false;
        }).toList();
        dept.setChildren(children);
        return dept;
    }
}
