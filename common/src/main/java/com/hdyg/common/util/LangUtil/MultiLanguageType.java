package com.hdyg.common.util.LangUtil;

/**
 * @author CZB
 * @describe 多语言类型 - 只处理了 英文、中文
 * @time 2018/12/26 15:34
 */

public enum MultiLanguageType {

    EN("en"),ZH("zh");

    public String typeKey;

    MultiLanguageType(String typeKey){
        this.typeKey = typeKey;
    }

    public static MultiLanguageType match(String code){
        if (code == null){
            return null;
        }
        for(MultiLanguageType type: values()){
            if(type.typeKey.equals(code)){
                return type;
            }
        }
        return null;
    }

}
