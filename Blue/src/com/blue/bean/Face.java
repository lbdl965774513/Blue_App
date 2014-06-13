package com.blue.bean;

/**
 * @author SLJM
 * @create 2014-4-22
 * @desc 表情对象实体
 *
 */
public class Face {
	
	/** 表情资源图片对应的ID */
    private int face_id;

    /** 表情资源对应的文字描述 */
    private String character;

    /** 表情资源的文件名 */
    private String faceName;

    /** 表情资源图片对应的ID */
    public int getId() {
        return face_id;
    }

    /** 表情资源图片对应的ID */
    public void setId(int face_id) {
        this.face_id=face_id;
    }

    /** 表情资源对应的文字描述 */
    public String getCharacter() {
        return character;
    }

    /** 表情资源对应的文字描述 */
    public void setCharacter(String character) {
        this.character=character;
    }

    /** 表情资源的文件名 */
    public String getFaceName() {
        return faceName;
    }

    /** 表情资源的文件名 */
    public void setFaceName(String faceName) {
        this.faceName=faceName;
    }
	
}
