package com.ruxuanwo.data.export.utils;


import com.ruxuanwo.data.export.dto.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Chenbin
 */
public class TreeUtil {

    private static String ROOT_ID = "0";

    private TreeUtil(){}

    /**
     * 将传过来的节点集合转换为树结构，
     * @param nodes
     * @param pid
     * @return
     */
    public static List<TreeNode> convert(List<TreeNode> nodes, String pid){
        if(CollectionUtils.isEmpty(nodes)){
            return null;
        }
        TreeNode treeNode = new TreeNode();
        convert(nodes, treeNode, ROOT_ID);
        return treeNode.getChildren();
    }
    /**
     * 将treeNode集合根据pid转换成一个树形结构
     * @param nodes
     * @return
     */
    private static  void convert(List<TreeNode> nodes, TreeNode treeNode, String pid) {
        if (nodes == null || nodes.isEmpty() || StringUtils.isEmpty(pid)) {
            return;
        }
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode node = nodes.get(i);
            if (isParentAndSonByPid(pid, node)) {
                // 添加到孩子节点列表
                treeNode.addChild(node);
                nodes.remove(node);
                i = -1;
                // 递归，实现无限层级*//*
                convert(nodes, node, node.getId());
            }

        }
    }

    private static  Boolean isParentAndSonByPid(String pid, TreeNode node) {
        if (ROOT_ID.equals(pid)) {
            if ( node!= null && pid.equals(node.getpId())) {
                return true;
            }
        } else {
            if (node != null && pid != null && (!ROOT_ID.equals(node.getpId())) && (pid.startsWith(node.getpId()))) {
                return true;
            }
        }
        return false;
    }
}
