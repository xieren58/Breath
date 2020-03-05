package com.zkp.breath.designpattern.factory.abstracts;

import com.zkp.breath.designpattern.factory.simple.DratLanguageImp;
import com.zkp.breath.designpattern.factory.simple.ILanguage;
import com.zkp.breath.designpattern.factory.simple.JavaLanguageImp;
import com.zkp.breath.designpattern.factory.simple.KotlinLanguageImp;

public class DesignPatternsFactory extends AbstractFactory {
    @Override
    public ILanguage getLanguage(int flag) {
        return null;
    }

    @Override
    public IDesignPatterns getDesignPattern(int flag) {

        IDesignPatterns iDesignPatterns = null;
        switch (flag) {
            case 1:
                iDesignPatterns = new SignletonDesignPatternsImp();
                break;
            case 2:
                iDesignPatterns = new StategyDesignPatternsImp();
                break;
            case 3:
                iDesignPatterns = new AdapterDesignPatternsImp();
                break;
            default:
                break;
        }
        return iDesignPatterns;
    }
}
