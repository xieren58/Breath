package com.zkp.breath.designpattern.factory.method;

import com.zkp.breath.designpattern.factory.simple.ILanguage;
import com.zkp.breath.designpattern.factory.simple.KotlinLanguageImp;

public class KotlinLanguageFactory implements ILanguageFactory {
    @Override
    public ILanguage createLanguage() {
        return new KotlinLanguageImp();
    }
}
