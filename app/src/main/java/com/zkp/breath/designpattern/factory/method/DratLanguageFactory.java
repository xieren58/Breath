package com.zkp.breath.designpattern.factory.method;

import com.zkp.breath.designpattern.factory.simple.DratLanguageImp;
import com.zkp.breath.designpattern.factory.simple.ILanguage;

public class DratLanguageFactory implements ILanguageFactory {
    @Override
    public ILanguage createLanguage() {
        return new DratLanguageImp();
    }
}
