package com.zkp.breath.designpattern.factory;

/**
 * 工厂类
 */
public class LanguageFactory {

    public static ILanguage getInstance(int flag) {
        ILanguage iLanguage = null;
        switch (flag) {
            case 1:
                iLanguage = new JavaLanguageImp();
                break;
            case 2:
                iLanguage = new KotlinLanguageImp();
                break;
            case 3:
                iLanguage = new DratLanguageImp();
                break;
            default:
                break;
        }

        return iLanguage;
    }

}
