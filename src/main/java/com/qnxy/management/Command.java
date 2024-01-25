package com.qnxy.management;

/**
 * 命令公共抽象接口
 * 
 * @author Qnxy
 */
public interface Command {

    /**
     * 命编号
     */
    int cmdNum();

    /**
     * 命令描述
     */
    String cmdDesc();
    
}
