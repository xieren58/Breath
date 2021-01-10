package com.zkp.breath.designpattern.factory.method;

import com.zkp.breath.designpattern.factory.simple.ILanguage;
import com.zkp.breath.designpattern.factory.simple.JavaLanguageImp;

public class JavaLanguageFactory implements ILanguageFactory {
    @Override
    public ILanguage createLanguage() {
        return new JavaLanguageImp();
    }
}
