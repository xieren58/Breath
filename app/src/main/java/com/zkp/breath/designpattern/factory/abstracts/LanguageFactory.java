package com.zkp.breath.designpattern.factory.abstracts;

import com.zkp.breath.designpattern.factory.simple.DratLanguageImp;
import com.zkp.breath.designpattern.factory.simple.ILanguage;
import com.zkp.breath.designpattern.factory.simple.JavaLanguageImp;
import com.zkp.breath.designpattern.factory.simple.KotlinLanguageImp;

public class LanguageFactory extends AbstractFactory {
    @Override
    public ILanguage getLanguage(int flag) {

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

    @Override
    public IDesignPatterns getDesignPattern(int flag) {
        return null;
    }
}
