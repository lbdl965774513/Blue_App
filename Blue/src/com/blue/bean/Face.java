package com.blue.bean;

/**
 * @author SLJM
 * @create 2014-4-22
 * @desc �������ʵ��
 *
 */
public class Face {
	
	/** ������ԴͼƬ��Ӧ��ID */
    private int face_id;

    /** ������Դ��Ӧ���������� */
    private String character;

    /** ������Դ���ļ��� */
    private String faceName;

    /** ������ԴͼƬ��Ӧ��ID */
    public int getId() {
        return face_id;
    }

    /** ������ԴͼƬ��Ӧ��ID */
    public void setId(int face_id) {
        this.face_id=face_id;
    }

    /** ������Դ��Ӧ���������� */
    public String getCharacter() {
        return character;
    }

    /** ������Դ��Ӧ���������� */
    public void setCharacter(String character) {
        this.character=character;
    }

    /** ������Դ���ļ��� */
    public String getFaceName() {
        return faceName;
    }

    /** ������Դ���ļ��� */
    public void setFaceName(String faceName) {
        this.faceName=faceName;
    }
	
}
